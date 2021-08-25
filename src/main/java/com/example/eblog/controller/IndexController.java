package com.example.eblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.eblog.domain.MPost;
import com.example.eblog.domain.MUser;
import com.example.eblog.domain.MUserAction;
import com.example.eblog.service.impl.MPostServiceImpl;
import com.example.eblog.service.impl.MUserActionServiceImpl;
import com.example.eblog.service.impl.ViewService;
import com.example.eblog.view.DetailCommentView;
import com.example.eblog.view.DetailView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    final MPostServiceImpl mPostService;
    final ViewService viewService;

    final MUserActionServiceImpl actionService;

    public IndexController(MPostServiceImpl service, ViewService viewService, MUserActionServiceImpl actionService) {
        this.mPostService = service;
        this.viewService = viewService;
        this.actionService = actionService;
    }

    @GetMapping({"/index", "", "/"})
    public String index(Model model) {
        System.out.println(viewService.getPostRef());
        model.addAttribute("posts", viewService.getPostRef());
        model.addAttribute("category", "index");
        model.addAttribute("status", "all");
        return "index";
    }

    @GetMapping("/notice")
    public String notice() {
        return "other/notice";
    }

    @GetMapping("/question")
    public String question(Model model) {
        model.addAttribute("posts", viewService.getPostRefByCategory(1));
        model.addAttribute("category", "question");
        model.addAttribute("status", "all");
        return "index";
    }
    @GetMapping("/question/doing")
    public String questionDoing(Model model) {
        model.addAttribute("posts", viewService.getPostRefByCategory(1));
        model.addAttribute("category", "question");
        model.addAttribute("status", "doing");
        return "index";
    }
    @GetMapping("/question/done")
    public String questionDone(Model model) {
        model.addAttribute("posts", viewService.getPostRefByCategory(1));
        model.addAttribute("category", "question");
        model.addAttribute("status", "done");
        return "index";
    }
    @GetMapping("/question/recommend")
    public String questionRecommend(Model model) {
        model.addAttribute("posts", viewService.getPostRefByCategory(1));
        model.addAttribute("category", "question");
        model.addAttribute("status", "recommend");
        return "index";
    }
    @GetMapping("/share")
    public String share(Model model) {
        model.addAttribute("posts", viewService.getPostRefByCategory(2));
        model.addAttribute("category", "share");
        model.addAttribute("status", "all");
        return "index";
    }
    @GetMapping("/share/doing")
    public String shareDoing(Model model) {
        model.addAttribute("posts", viewService.getPostRefByCategory(2));
        model.addAttribute("category", "share");
        model.addAttribute("status", "doing");
        return "index";
    }
    @GetMapping("/share/done")
    public String shareDone(Model model) {
        model.addAttribute("posts", viewService.getPostRefByCategory(2));
        model.addAttribute("category", "share");
        model.addAttribute("status", "done");
        return "index";
    }
    @GetMapping("/share/recommend")
    public String shareRecommend(Model model) {
        model.addAttribute("posts", viewService.getPostRefByCategory(2));
        model.addAttribute("category", "share");
        model.addAttribute("status", "recommend");
        return "index";
    }
    @GetMapping("/talk")
    public String talk(Model model) {
        model.addAttribute("posts", viewService.getPostRefByCategory(3));
        model.addAttribute("category", "talk");
        model.addAttribute("status", "all");
        return "index";
    }
    @GetMapping("/talk/doing")
    public String talkDoing(Model model) {
        model.addAttribute("posts", viewService.getPostRefByCategory(3));
        model.addAttribute("category", "talk");
        model.addAttribute("status", "doing");
        return "index";
    }
    @GetMapping("/talk/done")
    public String talkDone(Model model) {
        model.addAttribute("posts", viewService.getPostRefByCategory(3));
        model.addAttribute("category", "talk");
        model.addAttribute("status", "done");
        return "index";
    }
    @GetMapping("/talk/recommend")
    public String talkRecommend(Model model) {
        model.addAttribute("posts", viewService.getPostRefByCategory(3));
        model.addAttribute("category", "talk");
        model.addAttribute("status", "recommend");
        return "index";
    }
    @GetMapping("/suggest")
    public String suggest(Model model) {
        model.addAttribute("posts", viewService.getPostRefByCategory(4));
        model.addAttribute("category", "suggest");
        model.addAttribute("status", "all");
        return "index";
    }
    @GetMapping("/suggest/doing")
    public String suggestDoing(Model model) {
        model.addAttribute("posts", viewService.getPostRefByCategory(4));
        model.addAttribute("category", "suggest");
        model.addAttribute("status", "doing");
        return "index";
    }
    @GetMapping("/suggest/done")
    public String suggestDone(Model model) {
        model.addAttribute("posts", viewService.getPostRefByCategory(4));
        model.addAttribute("category", "suggest");
        model.addAttribute("status", "done");
        return "index";
    }
    @GetMapping("/suggest/recommend")
    public String suggestRecommend(Model model) {
        model.addAttribute("posts", viewService.getPostRefByCategory(4));
        model.addAttribute("category", "suggest");
        model.addAttribute("status", "recommend");
        return "index";
    }
    @GetMapping("/detail")
    public String detail(HttpSession session, @RequestParam(value = "postId") int postId, Model model) {
        DetailView detail  = viewService.getDetailViewByPostId(postId);
        List<DetailCommentView> comments = viewService.getCommentViewBy(postId);

        if (session.getAttribute("userLogin") != null) {
            Map<Long, DetailCommentView> map = new HashMap<>();
            for (DetailCommentView comment:
                    comments) {
                map.put((long) comment.getCommentId(), comment);
            }
            MUserAction action = new MUserAction();
            action.setUserId(((MUser)session.getAttribute("userLogin")).getId());
            Long actionPostId = (long) detail.getPostId();
            action.setPostId(actionPostId);
            List<MUserAction> actions = actionService.list(new QueryWrapper<>(action));

            for (MUserAction act:
                 actions) {
                map.get(act.getCommentId()).setZanok(true);
            }
        }

        model.addAttribute("detail", detail);
        model.addAttribute("comments", comments);

        return "jie/detail";
    }

    @GetMapping("/index/doing")
    public String indexDoing(Model model) {
        model.addAttribute("posts", viewService.getPostRef());
        model.addAttribute("category", "index");
        model.addAttribute("status", "doing");
        return "index";
    }

    @GetMapping("/index/done")
    public String indexDone(Model model) {
        model.addAttribute("posts", viewService.getPostRef());
        model.addAttribute("category", "index");
        model.addAttribute("status", "done");
        return "index";
    }

    @GetMapping("/index/recommend")
    public String indexRecommend(Model model) {
        model.addAttribute("posts", viewService.getPostRef());
        model.addAttribute("category", "index");
        model.addAttribute("status", "recommend");
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/newPost")
    public String newPost() {
        return "jie/add";
    }

    @GetMapping("/index/orderByTime")
    public String indexOrderByTime(Model model, @RequestParam(value = "category") String category, @RequestParam(value = "status") String status) {
        model.addAttribute("category", category);
        model.addAttribute("status", status);
        model.addAttribute("order", "time");
        if (category.equals("index")) {
            model.addAttribute("posts", viewService.getPostRefByTimeOrder());
        } else if (category.equals("question")){
            model.addAttribute("posts", viewService.getPostRefByCategoryOrderByTime(1));
        } else if (category.equals("share")){
            model.addAttribute("posts", viewService.getPostRefByCategoryOrderByTime(2));
        } else if (category.equals("talk")){
            model.addAttribute("posts", viewService.getPostRefByCategoryOrderByTime(3));
        } else if (category.equals("suggestion")){
            model.addAttribute("posts", viewService.getPostRefByCategoryOrderByTime(4));
        }

        return "index";
    }

    @GetMapping("/index/orderByCommentCount")
    public String indexOrderByCommentCount(Model model, @RequestParam(value = "category") String category, @RequestParam(value = "status") String status) {
        model.addAttribute("category", category);
        model.addAttribute("status", status);
        model.addAttribute("order", "comment");
        if (category.equals("index")) {
            model.addAttribute("posts", viewService.getPostRefOrderByCommentCount());
        } else if (category.equals("question")){
            model.addAttribute("posts", viewService.getPostRefByCategoryOrderByCommentCount(1));
        } else if (category.equals("share")){
            model.addAttribute("posts", viewService.getPostRefByCategoryOrderByCommentCount(2));
        } else if (category.equals("talk")){
            model.addAttribute("posts", viewService.getPostRefByCategoryOrderByCommentCount(3));
        } else if (category.equals("suggestion")){
            model.addAttribute("posts", viewService.getPostRefByCategoryOrderByCommentCount(4));
        }

        return "index";
    }


}
