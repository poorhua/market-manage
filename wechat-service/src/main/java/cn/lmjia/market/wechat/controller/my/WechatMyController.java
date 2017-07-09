package cn.lmjia.market.wechat.controller.my;

import cn.lmjia.market.core.entity.Login;
import cn.lmjia.market.core.service.SystemService;
import cn.lmjia.market.dealer.service.AgentService;
import cn.lmjia.market.dealer.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author CJ
 */
@Controller
public class WechatMyController {

    @Autowired
    private TeamService teamService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private AgentService agentService;

    @GetMapping("/wechatOrderList")
    public String wechatOrderList() {
        return "wechat@orderList.html";
    }

    @GetMapping(SystemService.wechatMyURi)
    public String my(@AuthenticationPrincipal Login login, Model model) {
        myTeam(login, model);
        model.addAttribute("login", login);
        return "wechat@personalCenter.html";
    }

    @GetMapping(SystemService.wechatMyTeamURi)
    public String originMyTeam() {
        return "redirect:" + SystemService.wechatMyURi;
    }

    @GetMapping(SystemService.wechatMyTeamURi)
    @Transactional(readOnly = true)
    public String myTeam(@AuthenticationPrincipal Login login, Model model) {
        model.addAttribute("agentLevel", agentService.loginTitle(agentService.highestAgent(login)));
        // 微信头像 名字 等级
        model.addAttribute("allCount", teamService.all(login));

        int lowestLevel = systemService.systemLevel() - 1;
        model.addAttribute("count0", teamService.agents(login, lowestLevel - 3));
        // 所谓的省代 其实是区(特殊的title)
//        model.addAttribute("count1", teamService.agents(login, lowestLevel - 1));
        // 最低等级（经销商）
        model.addAttribute("count2", teamService.agents(login, lowestLevel));
        // 客户
        model.addAttribute("count3", teamService.validCustomers(login));
        return "wechat@myTeam.html";
    }
}
