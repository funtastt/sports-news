package ru.kpfu.itis.asadullin.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.asadullin.model.entity.Friend;
import ru.kpfu.itis.asadullin.model.service.FriendService;
import ru.kpfu.itis.asadullin.model.service.UserService;
import ru.kpfu.itis.asadullin.util.dto.UserDto;

import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/find_friends")
public class FindFriendsController {

    private final UserService userService;
    private final FriendService friendService;

    @Autowired
    public FindFriendsController(UserService userService, FriendService friendService) {
        this.userService = userService;
        this.friendService = friendService;
    }

    @GetMapping
    public String getFindFriendsPage(Model model, HttpSession session) {
        boolean isLoggedIn = (boolean) session.getAttribute("isLoggedIn");

        if (!isLoggedIn) {
            return "redirect:/login";
        }

        int currentUserId = (int) session.getAttribute("userId");
        List<UserDto> allUsers = userService.getAll();
        List<UserDto> friendsDto = new ArrayList<>();
        List<Friend> friends = friendService.getAllForUserId(currentUserId);

        for (Friend friend : friends) {
            int id = friend.getFriendId();
            UserDto userDto = userService.getById(id);
            friendsDto.add(userDto);
        }

        for (UserDto user : allUsers) {
            user.setAdded(friendService.isFriendAdded(new Friend(currentUserId, user.getUserId())));
        }

        allUsers.removeIf(user -> user.getUserId() == currentUserId);

        model.addAttribute("isLoggedIn", true);
        model.addAttribute("users", allUsers);
        model.addAttribute("friends", friendsDto);

        return "ftl/findFriends";
    }

    @PostMapping
    public void searchUsers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String lambda = req.getParameter("searchText");
        List<UserDto> allUsers = userService.getAll();
        List<UserDto> matchedUsers = new ArrayList<>();

        for (UserDto user : allUsers) {
            if (user.getUsername().toLowerCase().contains(lambda.toLowerCase())) {
                matchedUsers.add(user);
            }
        }

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(matchedUsers);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        out.print(jsonResponse);
        out.close();
    }
}
