package tr.com.swe599;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j(topic = "html-operations")

@Service
public class HtmlSyntaxCheckerService {

    @Value("${html-syntax-checker-url}")
    private String htmlSyntaxCheckerUrl;

    public CheckSyntaxDto checkSyntax(String html) {

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("html", html);

            HttpEntity<MultiValueMap<String, String>> request =
                    new HttpEntity<>(map, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(htmlSyntaxCheckerUrl, request, String.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("" + response.getStatusCodeValue());
            }

            return (CheckSyntaxDto) JsonHelper.fromJsonString(response.getBody(), CheckSyntaxDto.class);
        } catch (Exception e) {
            log.error("", e);
        }

        return null;
    }
}
