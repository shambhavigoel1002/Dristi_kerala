package digit.util;

import digit.config.Configuration;
import digit.web.models.GenerateSummonsRequest;
import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class PdfServiceUtil {

    private final RestTemplate restTemplate;

    private final Configuration config;

    @Autowired
    public PdfServiceUtil(RestTemplate restTemplate, Configuration config) {
        this.restTemplate = restTemplate;
        this.config = config;
    }

    public ByteArrayResource generatePdfFromPdfService(GenerateSummonsRequest generateSummonsRequest, String tenantId,
                                                       String pdfTemplateKey) {
        try {
            StringBuilder uri = new StringBuilder();
            uri.append(config.getPdfServiceHost())
                    .append(config.getPdfServiceEndpoint())
                    .append("?tenantId=").append(tenantId).append("&key=").append(pdfTemplateKey);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<GenerateSummonsRequest> requestEntity = new HttpEntity<>(generateSummonsRequest, headers);

            ResponseEntity<ByteArrayResource> responseEntity = restTemplate.postForEntity(uri.toString(),
                    requestEntity, ByteArrayResource.class);

            return responseEntity.getBody();
        } catch (Exception e) {
            log.error("Error getting response from Pdf Service", e);
            throw new CustomException("SU_PDF_APP_ERROR", "Error getting response from Pdf Service");
        }
    }
}
