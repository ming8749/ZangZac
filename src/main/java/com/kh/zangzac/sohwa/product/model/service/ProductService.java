package com.kh.zangzac.sohwa.product.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.ming.member.model.vo.Member;
import com.kh.zangzac.sohwa.product.model.vo.Attachment;
import com.kh.zangzac.sohwa.product.model.vo.Cart;
import com.kh.zangzac.sohwa.product.model.vo.Option;
import com.kh.zangzac.sohwa.product.model.vo.Payment;
import com.kh.zangzac.sohwa.product.model.vo.Product;
import com.kh.zangzac.sohwa.product.model.vo.Qna;
import com.kh.zangzac.sohwa.product.model.vo.Review;

public interface ProductService {

	int insertProduct(Product p);

	int insertProductPhoto(ArrayList<Attachment> coreList);

	int insertOption(ArrayList<Option> list);


	ArrayList<Product> selectProductList(PageInfo pi, HashMap<String, String> categoryMap);

	ArrayList<Attachment> selectPhotoList(String categoryNo);

	Product selectProductDetail(int productNo);

	ArrayList<Attachment> selectPhotoDetail(int productNo);

	ArrayList<Option> optionDetail(int productNo);


	ArrayList<Product> searchProduct(PageInfo pi, HashMap<String, String> map);

	ArrayList<Attachment> searchPhoto(HashMap<String, String> map);

	ArrayList<Product> selectAllProduct();

	ArrayList<Attachment> selectAllPhoto();

	ArrayList<Option> selectProductOption(int productNo);

	ArrayList<Product> selectDeleteProduct();

	ArrayList<Attachment> selectDeletePhoto();

	int insertCart(Cart c);

	ArrayList<Cart> memberCart(String id);

	ArrayList<Option> selectAllOption();

	int deleteCart(int cartKeyNo);

	int deleteCarts(List<String> cartKeyNos);

	int updateCartEno(Cart c);

	int insertQna(Qna q);

	ArrayList<Qna> selectMyQna(String id);

	int getListQnaCount(HashMap<String, String> map);

	ArrayList<Qna> selectQna();

	int updateAnswer(Qna q);

	ArrayList<Qna> selectProductQna(int productNo);

	int deleteQna(int questionNo);

	int getListQnaYCount(HashMap<String, String> map);

	ArrayList<Qna> selectQnaY();

	ArrayList<Qna> searchKeyword(HashMap<String, String> map, PageInfo pi);

	ArrayList<Qna> searchYKeyword(HashMap<String, String> map, PageInfo pi);

	int insertReview(Review r);

	int insertReviewPhoto(Attachment a);

	ArrayList<Review> selectProductReview(int productNo);

	ArrayList<Attachment> selectPhotoReview(int productNo);

	ArrayList<Member> selectReviewMember(int productNo);

	ArrayList<Review> selectProductPhotoReview(int productNo);

	int[] selectAllProductScore(int productNo);

	int updateScore(Product p);

	ArrayList<Review> selectproductAllReview();

	int deleteReview(int reviewNo);

	String deleteSelectReview(int reviewNo);

	int updateReviewInfo(Review r);

	int updateReviewPhoto(Attachment a);

	void deleteReviewPhoto(int reviewNo);


	ArrayList<Attachment> searchPhototh(HashMap<String, String> searchMap);

	int updateYProduct(ArrayList<String> checkBoxArr);

	ArrayList<String> selectYPhoto(ArrayList<String> checkBoxArr);

	void updateYPhoto(ArrayList<String> checkBoxArr);

	int deleteOption(int productNo);

	int deleteProductPhoto(ArrayList<String> delRename);

	int updateProductPhotoY(ArrayList<Attachment> coreList);

	int updateProduct(Product p);

	int updatePhotoLevel(int productNo);

	ArrayList<Product> selectAllNProduct();

	int deleteQuestion(int questionNo);

	int updateYOption(ArrayList<String> checkBoxArr);

	int updateYCart(ArrayList<String> checkBoxArr);

	int updateYQna(ArrayList<String> checkBoxArr);

	int updateYReview(ArrayList<String> checkBoxArr);

	ArrayList<Cart> selectCart(String id);

	int insertPayment(ArrayList<Payment> paList);

	ArrayList<Payment> selectMyOrder(String id);

	ArrayList<Integer> selectOrderNo(String id);

	ArrayList<Payment> selectPayment(int orderNo);

	Payment selectBuyerInfo(int orderNo);
	
	Payment selectOrderDetail(int orderKeyNo);

	int updateDeliveryStatus(HashMap<String, String> map);

	int deleteOrder(int orderNo);

	int getListCountProduct(HashMap<String, String> map);

	ArrayList<Product> selectAdminProduct(HashMap<String, String> map, PageInfo pi);

	int refundOrder(int orderNo);

	ArrayList<Payment> selectAllPayment(HashMap<String, String> map, PageInfo pi);
	
	ArrayList<Payment> selectNPayment(HashMap<String, String> map, PageInfo pi);

	int getListCountOrder(HashMap<String, String> map);

	int getListCountOrderN(HashMap<String, String> map);

	int getListCount(HashMap<String, String> map);

	ArrayList<Product> selectProductMap(PageInfo pi, HashMap<String, String> map);

	ArrayList<Attachment> selectPhotoMap(HashMap<String, String> map);

	ArrayList<Attachment> selectPhotoTHMap(HashMap<String, String> map);

	ArrayList<Product> selectRecommendProduct();

	int purchaseYN(Payment p);

	String selectPaymentKey(int orderKeyNo);




	
}
