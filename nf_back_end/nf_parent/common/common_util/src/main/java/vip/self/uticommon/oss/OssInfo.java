package vip.self.uticommon.oss;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConditionalOnProperty(prefix = "myutil.oss", name = "effective", havingValue = "true")
@ConfigurationProperties(prefix = "myutil.oss")
public class OssInfo {
    private String endpoint;
    private String keyId;
    private String keySecret;
    private String bucketName;
}
