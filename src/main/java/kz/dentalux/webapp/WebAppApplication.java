package kz.dentalux.webapp;

import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class WebAppApplication {
//    private final static String GBD_SERVICE_UNAVAILABLE = "Сервис на стороне ГБД не доступен";
//    private final static String GBD_SERVICE_UNAVAILABLE_FAULT_CODE = "FAULT-015";
//    private final static Pattern FAULT015_PATTERN = Pattern.compile(GBD_SERVICE_UNAVAILABLE_FAULT_CODE);
//    private final static Pattern FAULT015_SERVICE_UNAVAILABLE_PTRN = Pattern.compile(GBD_SERVICE_UNAVAILABLE);
//    private final static Pattern FAULT015 = Pattern.compile("FAULT-015");
//    private final static Pattern FAULT015_SERVICE_UNAVAILABLE = Pattern.compile("");
    private String fault = "FAULT-015 - Ответ был неожиданным text/html";;

    public static void main(String[] args) {
        SpringApplication.run(WebAppApplication.class, args);
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    void setUTCTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

//    @Bean
//    CommandLineRunner runner() {
//        return args -> {
//            if(containsFault015("фывфвф FAULT-014(sdfafasda", "фывфвф FAULT-014(sdfafasda")){
//                System.out.println("found");
//            }
//            System.exit(0);
//        };
//    }
//
//
//    private boolean containsFault015(String faultCode, String faultString) {
//        return FAULT015_PATTERN.matcher(faultString).find()
//            || FAULT015_PATTERN.matcher(faultCode).find()
//            || FAULT015_SERVICE_UNAVAILABLE_PTRN.matcher(faultCode).find()
//            || FAULT015_SERVICE_UNAVAILABLE_PTRN.matcher(faultString).find();
//    }
}
