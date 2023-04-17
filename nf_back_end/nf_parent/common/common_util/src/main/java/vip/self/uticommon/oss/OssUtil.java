package vip.self.uticommon.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@ConditionalOnBean(OssInfo.class)
public class OssUtil {

    @Autowired
    private OssInfo temp;
    private static OssInfo ossInfo;
    public static DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

    @PostConstruct
    public void init() {
        ossInfo = temp;
    }

    /**
     * 上传文件到阿里云oss
     * @param file 文件
     * @return 文件路径
     */
    public static String uploadImg(MultipartFile file) {
        OSS ossClient = new OSSClientBuilder()
                .build(ossInfo.getEndpoint(),
                        ossInfo.getKeyId(),
                        ossInfo.getKeySecret());
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            String random = RandomStringUtils.randomAlphabetic(12);
            String path = df.format(new Date());
            String origin = file.getOriginalFilename();
            String suf = origin.substring(origin.lastIndexOf("."), origin.length());
            String fileName = path + "/" +  random + suf;
            ossClient.putObject(ossInfo.getBucketName(), fileName, inputStream);
            ossClient.shutdown();
            String url = "https://" + ossInfo.getBucketName() + "." + ossInfo.getEndpoint() + "/" +  fileName;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
