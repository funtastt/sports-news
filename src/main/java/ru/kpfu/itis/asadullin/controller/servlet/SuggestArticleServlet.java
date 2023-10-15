package ru.kpfu.itis.asadullin.controller.servlet;

import com.cloudinary.Cloudinary;
import ru.kpfu.itis.asadullin.model.dao.impl.ArticleDaoImpl;
import ru.kpfu.itis.asadullin.model.entity.Article;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.HashMap;

import static ru.kpfu.itis.asadullin.controller.servlet.AllNewsServlet.findUserIdInCookie;
import static ru.kpfu.itis.asadullin.controller.util.CloudinaryUtil.getCloudinary;

@WebServlet(name = "suggestArticleServlet", urlPatterns = "/suggest")
@MultipartConfig
public class SuggestArticleServlet extends HttpServlet {
    Cloudinary cloudinary = getCloudinary();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("ftl/suggest_article.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part titlePart = req.getPart("articleTitle");
        String title = extractFormData(titlePart);

        Part contentPart = req.getPart("articleContent");
        String content = extractFormData(contentPart);

        Part summaryPart = req.getPart("articleSummary");
        String summary = extractFormData(summaryPart);

        Part categoryPart = req.getPart("articleCategory");
        String category = extractFormData(categoryPart);

        Part imagePart = req.getPart("articleImage");
        String imageUrl = getUploadedImageUrl(imagePart);

        int authorId = findUserIdInCookie(req);
        Timestamp publishTime = new Timestamp(System.currentTimeMillis());

        Article article = new Article(title, content, summary, authorId, publishTime, category, imageUrl);

        ArticleDaoImpl articleDao = new ArticleDaoImpl();
        articleDao.insert(article);

        resp.sendRedirect("/news");
    }

    private String getUploadedImageUrl(Part part) throws IOException {
        String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();

        File file = File.createTempFile("/tmp" + File.separator + (filename.hashCode() % 100) + File.separator + filename, "");

        InputStream content = part.getInputStream();
        file.getParentFile().mkdirs();
        file.createNewFile();
        FileOutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[content.available()];
        content.read(buffer);
        out.write(buffer);
        file.deleteOnExit();
        out.close();

        return cloudinary.uploader().upload(file, new HashMap<>()).get("secure_url").toString();
    }

    private String extractFormData(Part part) throws IOException{
        StringBuilder result = new StringBuilder();
        if (part != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8"))) {
                char[] buffer = new char[1024];
                int bytesRead;
                while ((bytesRead = reader.read(buffer)) != -1) {
                    result.append(buffer, 0, bytesRead);
                }
            }
        }
        return result.toString();
    }
}
