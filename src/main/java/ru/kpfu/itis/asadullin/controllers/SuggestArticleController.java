package ru.kpfu.itis.asadullin.controllers;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.asadullin.model.dao.ArticleDao;
import ru.kpfu.itis.asadullin.model.entity.Article;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.HashMap;

@Controller
@RequestMapping("/suggest")
public class SuggestArticleController {
    private final Cloudinary cloudinary;
    private final ArticleDao articleDao;

    @Autowired
    public SuggestArticleController(Cloudinary cloudinary, ArticleDao articleDao) {
        this.cloudinary = cloudinary;
        this.articleDao = articleDao;
    }

    @GetMapping
    public String getSuggestArticleForm(HttpServletRequest request) {
        boolean isLoggedIn = (boolean) request.getAttribute("isLoggedIn");
        if (!isLoggedIn) {
            return "redirect:/login";
        }
        return "suggestArticle";
    }

    @PostMapping
    public String suggestArticle(
            HttpServletRequest request,
            MultipartFile articleImage,
            RedirectAttributes redirectAttributes
    ) throws IOException {
        String title = request.getParameter("articleTitle");
        String content = request.getParameter("articleContent");
        String summary = request.getParameter("articleSummary");
        String category = request.getParameter("articleCategory");

        HttpSession session = request.getSession();
        int authorId = (int) session.getAttribute("userId");
        Timestamp publishTime = new Timestamp(System.currentTimeMillis());

        String imageUrl = getUploadedImageUrl(articleImage);

        Article article = new Article(title, content, summary, authorId, publishTime, category, imageUrl);

        articleDao.insert(article);

        return "redirect:/news";
    }

    private String getUploadedImageUrl(MultipartFile file) throws IOException {
        String filename = Paths.get(file.getOriginalFilename()).getFileName().toString();
        File tempFile = File.createTempFile("/tmp" + File.separator + (filename.hashCode() % 100) + File.separator + filename, "");

        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            out.write(file.getBytes());
        }

        return cloudinary.uploader().upload(tempFile, new HashMap<>()).get("secure_url").toString();
    }
}
