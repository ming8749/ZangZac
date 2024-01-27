package com.kh.zangzac.sohwa.product.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.ming.member.model.vo.Member;
import com.kh.zangzac.sohwa.product.model.dao.ProductDAO;
import com.kh.zangzac.sohwa.product.model.vo.Attachment;
import com.kh.zangzac.sohwa.product.model.vo.Cart;
import com.kh.zangzac.sohwa.product.model.vo.Option;
import com.kh.zangzac.sohwa.product.model.vo.Payment;
import com.kh.zangzac.sohwa.product.model.vo.Product;
import com.kh.zangzac.sohwa.product.model.vo.Qna;
import com.kh.zangzac.sohwa.product.model.vo.Review;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductDAO pDAO;

	@Override
	public int insertProduct(Product p) {
		return pDAO.insertProduct(p);
		
	}

	@Override
	public int insertProductPhoto(ArrayList<Attachment> coreList) {
		return pDAO.insertProductPhoto(coreList);
	}

	@Override
	public int insertOption(ArrayList<Option> list) {
		return pDAO.insertOption(list);
	}


	@Override
	public ArrayList<Product> selectProductList(PageInfo pi, HashMap<String, String> map) {
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, limit);
		return pDAO.selectProductList(map, rowBounds);
	}

	@Override
	public ArrayList<Attachment> selectPhotoList(String categoryNo) {
		return pDAO.selectPhotoList(categoryNo);
	}

	@Override
	public Product selectProductDetail(int productNo) {
		return pDAO.selectProductDetail(productNo);
	}

	@Override
	public ArrayList<Attachment> selectPhotoDetail(int productNo) {
		return pDAO.selectPhotoDetail(productNo);
	}

	@Override
	public ArrayList<Option> optionDetail(int productNo) {
		return pDAO.optionDetail(productNo);
	}


	@Override
	public ArrayList<Product> searchProduct(PageInfo pi, HashMap<String, String> map) {
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, limit);
		return pDAO.searchProduct(map, rowBounds);
	}

	@Override
	public ArrayList<Attachment> searchPhoto(HashMap<String, String> map) {
		return pDAO.searchPhoto(map);
	}

	@Override
	public ArrayList<Product> selectAllProduct() {
		return pDAO.selectAllProduct();
	}

	@Override
	public ArrayList<Attachment> selectAllPhoto() {
		return pDAO.selectAllPhoto();
	}

	@Override
	public ArrayList<Option> selectProductOption(int productNo) {
		return pDAO.selectProductOption(productNo);
	}

	@Override
	public ArrayList<Product> selectDeleteProduct() {
		return pDAO.selectDeleteProduct();
	}

	@Override
	public ArrayList<Attachment> selectDeletePhoto() {
		return pDAO.selectDeletePhoto();
	}

	@Override
	public int insertCart(Cart c) {
		return pDAO.insertCart(c);
	}

	@Override
	public ArrayList<Cart> memberCart(String id) {
		return pDAO.memberCart(id);
	}

	@Override
	public ArrayList<Option> selectAllOption() {
		return pDAO.selectAllOption();
	}

	@Override
	public int deleteCart(int cartKeyNo) {
		return pDAO.deleteCart(cartKeyNo);
	}

	@Override
	public int deleteCarts(List<String> cartKeyNos) {
		return pDAO.deleteCarts(cartKeyNos);
	}

	@Override
	public int updateCartEno(Cart c) {
		return pDAO.updateCartEno(c);
	}

	@Override
	public int insertQna(Qna q) {
		return pDAO.insertQna(q);
	}

	@Override
	public ArrayList<Qna> selectMyQna(String id) {
		return pDAO.selectMyQna(id);
	}

	@Override
	public int getListQnaCount(HashMap<String, String> map) {
		return pDAO.getListQnaCount(map);
	}

	@Override
	public ArrayList<Qna> selectQna() {
		return pDAO.selectQna();
	}

	@Override
	public int updateAnswer(Qna q) {
		return pDAO.updateAnswer(q);
	}

	@Override
	public ArrayList<Qna> selectProductQna(int productNo) {
		return pDAO.selectProductQna(productNo);
	}

	@Override
	public int deleteQna(int questionNo) {
		return pDAO.deleteQna(questionNo);
	}

	@Override
	public int getListQnaYCount(HashMap<String, String> map) {
		return pDAO.getListQnaYCount(map);
	}

	@Override
	public ArrayList<Qna> selectQnaY() {
		return pDAO.selectQnaY();
	}

	@Override
	public ArrayList<Qna> searchKeyword(HashMap<String, String> map, PageInfo pi) {
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, limit);
		return pDAO.searchKeyword(map, rowBounds);
	}

	@Override
	public ArrayList<Qna> searchYKeyword(HashMap<String, String> map, PageInfo pi) {
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, limit);
		return pDAO.searchYKeyword(map, rowBounds);
	}

	@Override
	public int insertReview(Review r) {
		return pDAO.insertReview(r);
	}

	@Override
	public int insertReviewPhoto(Attachment a) {
		return pDAO.insertReviewPhoto(a);
	}

	@Override
	public ArrayList<Review> selectProductReview(int productNo) {
		return pDAO.selectProductReview(productNo);
	}

	@Override
	public ArrayList<Attachment> selectPhotoReview(int productNo) {
		return pDAO.selectPhotoReview(productNo);
	}

	@Override
	public ArrayList<Member> selectReviewMember(int productNo) {
		return pDAO.selectReviewMember(productNo);
	}

	@Override
	public ArrayList<Review> selectProductPhotoReview(int productNo) {
		return pDAO.selectProductPhotoReview(productNo);
	}

	@Override
	public int[] selectAllProductScore(int productNo) {
		return pDAO.selectAllProductScore(productNo);
	}

	@Override
	public int updateScore(Product p) {
		return pDAO.updateScore(p);
	}

	@Override
	public ArrayList<Review> selectproductAllReview() {
		return pDAO.selectProductAllReview();
	}

	@Override
	public int deleteReview(int reviewNo) {
		return pDAO.deleteReview(reviewNo);
	}

	@Override
	public String deleteSelectReview(int reviewNo) {
		return pDAO.deleteSelectReview(reviewNo);
	}

	@Override
	public int updateReviewInfo(Review r) {
		return pDAO.updateReviewInfo(r);
	}

	@Override
	public int updateReviewPhoto(Attachment a) {
		return pDAO.updateReviewPhoto(a);
	}

	@Override
	public void deleteReviewPhoto(int reviewNo) {
		pDAO.deleteReviewPhoto(reviewNo);
	}


	@Override
	public ArrayList<Attachment> searchPhototh(HashMap<String, String> searchMap) {
		return pDAO.searchPhototh(searchMap);
	}

	@Override
	public int updateYProduct(ArrayList<String> checkBoxArr) {
		return pDAO.updateYProduct(checkBoxArr);
	}

	@Override
	public ArrayList<String> selectYPhoto(ArrayList<String> checkBoxArr) {
		return pDAO.selectYPhoto(checkBoxArr);
	}

	@Override
	public void updateYPhoto(ArrayList<String> checkBoxArr) {
		pDAO.updateYPhoto(checkBoxArr);
	}

	@Override
	public int deleteOption(int productNo) {
		return pDAO.deleteOption(productNo);
		
	}

	@Override
	public int deleteProductPhoto(ArrayList<String> delRename) {
		return pDAO.deleteProductPhoto(delRename);
	}

	@Override
	public int updateProductPhotoY(ArrayList<Attachment> coreList) {
		return pDAO.updateProductPhotoY(coreList);
	}

	@Override
	public int updateProduct(Product p) {
		return pDAO.updateProduct(p);
	}

	@Override
	public int updatePhotoLevel(int productNo) {
		return pDAO.updatePhotoLevel(productNo);
		
	}

	@Override
	public ArrayList<Product> selectAllNProduct() {
		return pDAO.selectAllNProduct();
	}

	@Override
	public int deleteQuestion(int questionNo) {
		return pDAO.deleteQuestion(questionNo);
	}

	@Override
	public int updateYOption(ArrayList<String> checkBoxArr) {
		return pDAO.updateYOption(checkBoxArr);
	}

	@Override
	public int updateYCart(ArrayList<String> checkBoxArr) {
		return pDAO.updateYCart(checkBoxArr);
	}

	@Override
	public int updateYQna(ArrayList<String> checkBoxArr) {
		return pDAO.updateYQna(checkBoxArr);
	}

	@Override
	public int updateYReview(ArrayList<String> checkBoxArr) {
		return pDAO.updateYReview(checkBoxArr);
	}

	@Override
	public ArrayList<Cart> selectCart(String id) {
		return pDAO.selectCart(id);
	}

	@Override
	public int insertPayment(ArrayList<Payment> paList) {
		return pDAO.insertPayment(paList);
	}

	@Override
	public ArrayList<Payment> selectMyOrder(String id) {
		return pDAO.selectMyOrder(id);
	}

	@Override
	public ArrayList<Integer> selectOrderNo(String id) {
		return pDAO.selectOrderNo(id);
	}

	@Override
	public ArrayList<Payment> selectPayment(int orderNo) {
		return pDAO.selectPayment(orderNo);
	}

	@Override
	public Payment selectBuyerInfo(int orderNo) {
		return pDAO.selectBuyerInfo(orderNo);
	}

	@Override
	public ArrayList<Payment> selectAllPayment(HashMap<String, String> map, PageInfo pi) {
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, limit);
		return pDAO.selectAllPayment(map, rowBounds);
	}

	@Override
	public Payment selectOrderDetail(int orderKeyNo) {
		return pDAO.selectOrderDetail(orderKeyNo);
	}

	@Override
	public int updateDeliveryStatus(HashMap<String, String> map) {
		return pDAO.updateDeliveryStatus(map);
	}

	@Override
	public int deleteOrder(int orderNo) {
		return pDAO.deleteOrder(orderNo);
	}

	@Override
	public int getListCountProduct(HashMap<String, String> map) {
		return pDAO.getListCountProduct(map);
	}
	
	@Override
	public int refundOrder(int orderNo) {
		return pDAO.refundOrder(orderNo);
	}
	
	
	
	
	@Override
	public ArrayList<Product> selectAdminProduct(HashMap<String, String> map, PageInfo pi) {
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, limit);
		return pDAO.selectAdminProduct(map, rowBounds);
	}

	@Override
	public ArrayList<Payment> selectNPayment(HashMap<String, String> map, PageInfo pi) {
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, limit);
		return pDAO.selectNPayment(map,rowBounds);
	}

	@Override
	public int getListCountOrder(HashMap<String, String> map) {
		return pDAO.getListCountOrder(map);
	}

	@Override
	public int getListCountOrderN(HashMap<String, String> map) {
		return pDAO.getListCountOrderN(map);
	}

	@Override
	public int getListCount(HashMap<String, String> map) {
		return pDAO.getListCount(map);
	}

	@Override
	public ArrayList<Product> selectProductMap(PageInfo pi, HashMap<String, String> map) {
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		int limit = pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, limit);
		return pDAO.selectProductMap(map, rowBounds);
	}

	@Override
	public ArrayList<Attachment> selectPhotoMap(HashMap<String, String> map) {
		return pDAO.selectPhotoMap(map);
	}

	@Override
	public ArrayList<Attachment> selectPhotoTHMap(HashMap<String, String> map) {
		return pDAO.selectPhotoTHMap(map);
	}

	@Override
	public ArrayList<Product> selectRecommendProduct() {
		return pDAO.selectRecommendProduct();
	}

	@Override
	public int purchaseYN(Payment p) {
		return pDAO.purchaseYN(p);
	}

	@Override
	public String selectPaymentKey(int orderKeyNo) {
		return pDAO.selectPaymentKey(orderKeyNo);
	}





	

	
	
}
