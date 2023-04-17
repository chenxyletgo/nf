package vip.self.cliservice.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vip.self.uticommon.oss.OssUtil;
import vip.self.uticommon.result.R;

@RestController
@RequestMapping("/cliservice/common")
@CrossOrigin
public class CommonController {

    @PostMapping("/uploadImg")
    public R uploadImg(MultipartFile file) {
        String url = OssUtil.uploadImg(file);
        if (url == null) return R.error().setMessage("图片上传失败");
        return R.ok().data("url", url);
    }

}
