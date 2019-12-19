package cn.techaction.controller.backstage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionProductService;
import cn.techaction.utils.PageBean;

@Controller
@RequestMapping("/product")
public class ActionProductController {
	@Autowired
	private ActionProductService actionProductService;
	@RequestMapping("/find_product.do")
	@ResponseBody
	public SverResponse<PageBean<ActionProduct>> findProducts(
			Integer productId,Integer partsId,
			Integer pageNum,Integer pageSize) {
		//调用Service层的方法分页查询
		return actionProductService.findProduct(productId, partsId, pageNum, pageSize);
	}
}
