package org.donnguk.emodiary.utility;

import org.donnguk.emodiary.dto.type.ErrorCode;
import org.donnguk.emodiary.exception.CommonException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Component
public class ImageUtil {
    private final String IMAGE_CONTENT_PREFIX = "image/";

    @Value("${spring.image.path}")
    private String resourcePath;

    @Value("${server.https-address}")
    private String serverUrl;

    public String decodeAndUploadBase64(String base64Image) {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        String uuid = UUID.randomUUID().toString();

        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));

            ImageIO.write(bufferedImage, "png", new File(resourcePath + uuid + ".png"));
        } catch (IOException e) {
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

//        return serverUrl + "/images/" + uuid + ".png";
        return uuid + ".png";
    }


    public String uploadImageFile(MultipartFile file) {
        final String contentType = file.getContentType();

        assert contentType != null;
        String type = "." + contentType.substring(contentType.indexOf("/") + 1);

        if (!contentType.startsWith(IMAGE_CONTENT_PREFIX)) {
            throw new CommonException(ErrorCode.MISSING_REQUEST_PARAMETER);
        }

        String uuid = UUID.randomUUID().toString();

        try {
            file.transferTo(
                    new File(resourcePath + uuid + type));
        } catch (IOException e) {
            throw new CommonException(ErrorCode.UPLOAD_FILE_ERROR);
        }

//        return serverUrl + "/images/" + uuid + ".png";
        return uuid + type;
    }
}
