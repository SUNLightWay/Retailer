package cn.techaction.controller.protal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionProductService;
import cn.techaction.vo.ActionProductFloorVo;

@Controller
@RequestMapping("/product")
public class ActionProductController {
	
	@Autowired
	private ActionProductService actionProductService;
	
	/**
	 * 查询热销商品
	 * @param num
	 * @return
	 */
	@RequestMapping("/findhotproducts.do")
	@ResponseBody
	public SverResponse<List<ActionProduct>> findHotProducts(Integer num){
		
		return actionProductService.findHotProducts(num);
	}
	
	/**
	 * 查找楼层商品
	 * @return
	 */
	@RequestMapping("/findfloor.do")
	@ResponseBody
	public SverResponse<ActionProductFloorVo> findFloorProducts(){
		
		return actionProductService.findFloorProducts();
	}
	
	/**
	 * 根据商品编号获取商品详情
	 * @param productId
	 * @return
	 */
	@RequestMapping("/getdetail.do")
	@ResponseBody
	public SverResponse<ActionProduct> getProductDetail(Integer productId){
		return actionProductService.findProductDetailForPortal(productId);	

	}
}
