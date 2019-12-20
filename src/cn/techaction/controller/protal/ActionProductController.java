package cn.techaction.controller.protal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionProductService;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionProductFloorVo;
import cn.techaction.vo.ActionProductListVo;

@Controller
@RequestMapping("/product")
public class ActionProductController {
	@Autowired
	private ActionProductService aProductService;
	/**
	 * ��ѯ������Ʒ
	 * @param num
	 * @return
	 */
	@RequestMapping("/findhotproducts.do")
	@ResponseBody
	public SverResponse<List<ActionProduct>> findHotProducts(Integer num){
		return aProductService.findHotProducts(num);
	}
	/**
	 * ����¥����Ʒ
	 * @return
	 */
	@RequestMapping("/findfloor.do")
	@ResponseBody
	public SverResponse<ActionProductFloorVo> findFloorProducts(){
		return aProductService.findFloorProducts();
	}
	/**
	 * ������Ʒ��ţ���ȡ��Ʒ����
	 * @param productId
	 * @return
	 */
	@RequestMapping("/getdetail.do")
	@ResponseBody
	public SverResponse<ActionProduct> getProductDetail(Integer productId){
		return aProductService.findProductDetailForPortal(productId);
	}
	/**
	 * ����������ѯ��Ʒ
	 * @param productTypeId
	 * @param partsId
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/findproducts.do")
	@ResponseBody
	public SverResponse<PageBean<ActionProductListVo>> searchProducts(Integer productTypeId,
			Integer partsId,String name,
			@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
			@RequestParam(value ="pageSize",defaultValue="10") int pageSize){
		return aProductService.findProductsForProtal(productTypeId,partsId,name,pageNum,pageSize);
	}
}
