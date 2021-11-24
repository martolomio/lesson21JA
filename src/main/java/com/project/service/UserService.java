package com.project.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.project.dao.ApplicantRepository;
import com.project.dao.UserRepository;
import com.project.domain.Access;
import com.project.domain.Applicant;
import com.project.domain.User;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicantRepository applicantRepository;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    public User findById(Integer id) {
        return userRepository.findById(id).get();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean addUser(User user) {
        User userFromDb = userRepository.findByEmail(user.getEmail());

        if (userFromDb != null) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);
        user.setAccessLevels(Collections.singleton(Access.USER));
        user.setActivationCode(UUID.randomUUID().toString());

        userRepository.save(user);
        sendActivationCode(user);
        return true;
    }

    public void sendActivationCode(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Доброго дня, %s %s! \n\n" +
                            "Вітаємо в приймальній комісії.\n" +
                            "Для продовження реєстрації пройдіть за посиланням:\n" +
                            "http://localhost:8080/activate/%s",
                    user.getFirstName(),
                    user.getLastName(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Код активації", message);
        }
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActive(true);
        user.setActivationCode(null);

        userRepository.save(user);

        return true;
    }

    public void saveUser(User user, Map<String, String> form) {
        user.setFirstName(form.get("firstName"));
        user.setLastName(form.get("lastName"));
        user.setEmail(form.get("email"));

        if (form.keySet().contains("active")) {
            user.setActive(true);
        } else {
            user.setActive(false);
        }

        user.getAccessLevels().clear();

        Set<String> accessLevels = Arrays.stream(Access.values()).map(Access::name).collect(Collectors.toSet());

        for (String key : form.keySet()) {
            if (accessLevels.contains(key)) {
                user.getAccessLevels().add(Access.valueOf(key));
            }
        }

        userRepository.save(user);
    }

    public void updateProfile(User user, String firstName, String lastName, String email, String password,
                              String birthDate, String city, String school, MultipartFile photo, String removePhotoFlag) throws IOException {
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(passwordEncoder.encode(password));

        String userEmail = user.getEmail();
        boolean isEmailChanged = (email != null && !email.equals(userEmail))
                || (userEmail != null && !userEmail.equals(email));

        if (isEmailChanged) {
            user.setEmail(email);
            user.setActive(false);
            user.setActivationCode(UUID.randomUUID().toString());
            sendActivationCode(user);
        }

        if (user.getAccessLevels().contains(Access.valueOf("USER"))) {
            Optional<Applicant> applicantFromDb = applicantRepository.findById(user.getId());
            Applicant applicant = applicantFromDb.orElse(new Applicant());

            applicant.setId(user.getId());

            if (!birthDate.isEmpty()) {
                applicant.setBirthDate(LocalDate.parse(birthDate));
            }

            if (!city.isEmpty()) {
                applicant.setCity(city);
            }

            if (!school.isEmpty()) {
                applicant.setSchool(school);
            }

            boolean removePhoto = (removePhotoFlag == null) ? false : true;
            if (!photo.isEmpty() || !applicantFromDb.isPresent() || removePhoto) {
                applicant.setFileName(StringUtils.cleanPath(photo.getOriginalFilename()));
                applicant.setFileType(photo.getContentType());
                applicant.setFileData(photo.getBytes());
            }

            applicant.setUser(user);
            user.setApplicant(applicant);
        }

        userRepository.save(user);
    }

    public String parseFileData(User user) throws UnsupportedEncodingException {
        String fileBase64Encoded = new String();

        if (user.getApplicant() != null) {
            byte[] fileBytes = user.getApplicant().getFileData();
            byte[] fileEncodeBase64 = Base64.encodeBase64(fileBytes);
            fileBase64Encoded = new String(fileEncodeBase64, "UTF-8");
        }

        return fileBase64Encoded;
    }
}
