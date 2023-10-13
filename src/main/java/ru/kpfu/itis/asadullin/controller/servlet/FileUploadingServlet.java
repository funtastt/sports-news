package ru.kpfu.itis.asadullin.controller.servlet;

import com.cloudinary.Cloudinary;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;

import static ru.kpfu.itis.asadullin.controller.util.CloudinaryUtil.getCloudinary;

@WebServlet(name = "fileUploadingServlet", urlPatterns = "/upload")
@MultipartConfig(
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 2 * 5 * 1024 * 1024
)
public class FileUploadingServlet extends HttpServlet {
    Cloudinary cloudinary = getCloudinary();
    // TODO: Сохраняет в папке C:\tmp - исправить
    private static final String FILE_NAME_PREFIX = "/tmp";
    public static final int DIRECTORIES_COUNT = 100;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("ftl/upload.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart("fileUpload");
        String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();

        File file = File.createTempFile(FILE_NAME_PREFIX + File.separator + (filename.hashCode() % DIRECTORIES_COUNT) + File.separator + filename, "");

        InputStream content = part.getInputStream();
        file.getParentFile().mkdirs();
        file.createNewFile();

        FileOutputStream out = new FileOutputStream(file);

        byte[] buffer = new byte[content.available()];

        content.read(buffer);
        out.write(buffer);

        file.deleteOnExit();

        out.close();

        cloudinary.uploader().upload(file, new HashMap<>());
    }
}
