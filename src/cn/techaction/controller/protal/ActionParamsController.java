package cn.techaction.controller.protal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.techaction.common.SverResponse;
import cn.techaction.service.ActionParamsService;

@Controller
@RequestMapping("/param")
public class ActionParamsController {

	@Autowired
	private ActionParamsService actionParamsService;

}
