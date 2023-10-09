package ru.kpfu.itis.asadullin.service.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryUtil {
    private static Cloudinary cloudinary;

    public static Cloudinary getCloudinary() {
        if (cloudinary == null) {
            cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "dpyox6rua",
                    "api_key", "142716543924767",
                    "api_secret", "qB_A036AmAnaZUeQKALTJOcvnRg"));
        }

        return cloudinary;
    }
}
