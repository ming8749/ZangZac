package com.kh.zangzac.sohwa.product.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.kh.zangzac.common.ImageStorage;
import com.kh.zangzac.common.Pagination;
import com.kh.zangzac.common.model.vo.PageInfo;
import com.kh.zangzac.ming.member.model.vo.Member;
import com.kh.zangzac.sohwa.product.model.exception.ProductException;
import com.kh.zangzac.sohwa.product.model.service.ProductService;
import com.kh.zangzac.sohwa.product.model.vo.Attachment;
import com.kh.zangzac.sohwa.product.model.vo.Cart;
import com.kh.zangzac.sohwa.product.model.vo.Option;
import com.kh.zangzac.sohwa.product.model.vo.Payment;
import com.kh.zangzac.sohwa.product.model.vo.Product;
import com.kh.zangzac.sohwa.product.model.vo.Qna;
import com.kh.zangzac.sohwa.product.model.vo.Review;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import jakarta.servlet.http.HttpServletRequest;

@SessionAttributes("loginUser")
@Controller
public class ProductController {
   
   private final ImageStorage imageStorage;

    @Autowired
    public ProductController(ImageStorage imageStorage) {
        this.imageStorage = imageStorage;
    }
   
   @Autowired
   private ProductService pService;
   
   
   
   //관리자 상품 등록페이지 view
   @GetMapping("adminProductEnroll.so")
   public String adminProductEnrollView() {
      return "views/sohwa/(admin)productEnroll";
   }
   
   //관리자 상품 수정페이지 view
   @GetMapping("adminProductUpdate.so")
   public String adminProductUpdateView(@RequestParam("productNo") int productNo, Model model) {
      Product p = pService.selectProductDetail(productNo);
      ArrayList<Attachment> list = pService.selectPhotoDetail(productNo);
      ArrayList<Option> oList = pService.optionDetail(productNo);
      
      model.addAttribute("productNo", productNo);
      model.addAttribute("oList", oList);
      model.addAttribute("list", list);
      model.addAttribute("p", p);
      return "views/sohwa/(admin)productUpdate";
   }
   
   
   @GetMapping("adminQnaListView.so")
   public String adminQnaListView(@RequestParam(value="searchType", defaultValue="1") String searchType, @RequestParam(value="keyword", defaultValue="") String keyword, @RequestParam(value="status", defaultValue="1") int status, @RequestParam(value="page", defaultValue="1") int page, HttpServletRequest request, Model model) {
      
      if(status ==1) {
    	  PageInfo pi = new PageInfo();
          ArrayList<Qna> qList = new ArrayList<>();
          ArrayList<Product> pList = pService.selectAllProduct();
          ArrayList<Attachment> aList = pService.selectAllPhoto();
         
          if(keyword != null) {
             HashMap<String, String> map = new HashMap<>();
             map.put("keyword", keyword);
             map.put("searchType", searchType);
             int listCount = pService.getListQnaCount(map);
             int currentPage = page;
             pi = new PageInfo();
             pi = Pagination.getPageInfo(currentPage, listCount, 10);
             qList = pService.searchKeyword(map, pi);
          }else {
             qList = pService.selectQna();
          } 
          model.addAttribute("searchType", searchType);
          model.addAttribute("keyword", keyword);
          model.addAttribute("status", status);
          model.addAttribute("pList", pList);
          model.addAttribute("aList", aList);
          model.addAttribute("qList", qList);
          model.addAttribute("pi", pi);
          model.addAttribute("loc", request.getRequestURI());
          return "views/sohwa/(admin)qnaList";
         
      }else {
    	  PageInfo pi = new PageInfo();
          ArrayList<Qna> qList = new ArrayList<>();
          ArrayList<Product> pList = pService.selectAllProduct();
          ArrayList<Attachment> aList = pService.selectAllPhoto();
         
         if(keyword != null) {
             HashMap<String, String> map = new HashMap<>();
             map.put("keyword", keyword);
             map.put("searchType", searchType);
             int listCount = pService.getListQnaYCount(map);
             int currentPage = page;
             pi = new PageInfo();
             pi = Pagination.getPageInfo(currentPage, listCount, 10);
             qList = pService.searchYKeyword(map, pi);
         }else {
            qList = pService.selectQnaY();
         }
         model.addAttribute("searchType", searchType);
         model.addAttribute("keyword", keyword);
         model.addAttribute("status", status);
         model.addAttribute("pList", pList);
         model.addAttribute("aList", aList);
         model.addAttribute("qList", qList);
         model.addAttribute("pi", pi);
         model.addAttribute("loc", request.getRequestURI());
         return "views/sohwa/(admin)qnaList";
      }
      
   }
   
  
   
   //관리자 상품 목록페이지 view
   @GetMapping("adminProductList.so")
   public String adminProductListView(Model model, @RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="searchType", defaultValue="1") String searchType, @RequestParam(value="keyword", defaultValue="") String keyword, HttpServletRequest request) {
//      ArrayList<Product> pList = pService.selectAllProduct();
      ArrayList<Attachment> aList = pService.selectAllPhoto();
      int listCount = 0;
      HashMap<String, String> map = new HashMap<>();
      map.put("keyword", keyword);
      map.put("searchType", searchType);
      int currentPage = page;
      listCount = pService.getListCountProduct(map);
      PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);
      ArrayList<Product> pList = pService.selectAdminProduct(map, pi);
      
      //추천상품
      ArrayList<Product> recommend = pService.selectRecommendProduct();
      
      model.addAttribute("recommend", recommend);
      model.addAttribute("loc", request.getRequestURI());
      model.addAttribute("pi", pi);
      model.addAttribute("keyword", keyword);
      model.addAttribute("searchType", searchType);
      model.addAttribute("pageStatus", "Y");
      model.addAttribute("aList", aList);
      model.addAttribute("pList", pList);
      return "views/sohwa/(admin)productList";
   }
   
  
   
   
   
   
   
   
   @GetMapping("productListView.so")
   public String productListView(@RequestParam(value="keyword", defaultValue="") String keyword, @RequestParam(value="standard", defaultValue="1") String standard, @RequestParam(value="categoryNo", defaultValue="0") String categoryNo, @RequestParam(value="page", defaultValue="1") int page, Model model, HttpServletRequest request) {
      
      
      HashMap<String, String> map = new HashMap<>();
      
      
      map.put("keyword", keyword);
      map.put("categoryNo", categoryNo);
      map.put("standard", standard);
      
      int listCount = pService.getListCount(map);
      int currentPage = page;
      PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 12);
      ArrayList<Product> pList = pService.selectProductMap(pi, map);
      ArrayList<Attachment> aList = pService.selectPhotoMap(map);
      ArrayList<Attachment> thList = pService.selectPhotoTHMap(map);
      ArrayList<Review> rList = pService.selectproductAllReview();
      
      for(int i=0; i<pList.size(); i++) {
    	  for(int j=0; j<thList.size(); j++) {
    		  if(pList.get(i).getProductNo()==thList.get(j).getBoardNo() && thList.get(j).getPhotoLevel()==0) {
    			  pList.get(i).setPhotoPath(thList.get(j).getPhotoPath());
    		  }
    	  }
      }
      
      
      System.out.println(pi.getMaxPage());
      if(aList != null) {
         model.addAttribute("thList", thList);
         model.addAttribute("rList", rList);
         model.addAttribute("categoryNo", categoryNo);
         model.addAttribute("keyword", keyword);
         model.addAttribute("aList", aList);
         model.addAttribute("pList", pList);
         model.addAttribute("standard", standard);
         model.addAttribute("pi", pi);
         model.addAttribute("loc", request.getRequestURI());
         return "views/sohwa/productList";
      }else {
         throw new ProductException("상품 목록 조회 실패");
      }
      
   }
   
   
   
//   @GetMapping("searchProduct.so")
//   public String searchProduct(@RequestParam("keyword") String keyword, @RequestParam(value="standard", defaultValue="1") String standard, @RequestParam(value="page", defaultValue="1") int page, Model model, HttpServletRequest request) {
//      
//      int listCount = pService.getListCountKeyword(keyword);
//      
//      
//      
//      System.out.println(map);
//      
//      ArrayList<Product> pList = pService.searchProduct(pi, map);
//      ArrayList<Attachment> aList = pService.searchPhoto(map);
//      
//      
//      if(aList != null) {
//         model.addAttribute("keyword", keyword);
//         model.addAttribute("aList", aList);
//         model.addAttribute("pList", pList);
//         model.addAttribute("pi", pi);
//         model.addAttribute("loc", request.getRequestURI());
//         return "views/sohwa/productList";
//      }else {
//         throw new ProductException("상품 목록 조회 실패");
//      }
//   }
//   
   
   @GetMapping("infiniteScroll.so")
   @ResponseBody
   public HashMap<String, Object> infiniteScroll(@RequestParam(value="keyword", defaultValue="") String keyword, @RequestParam(value="standard", defaultValue="1") String standard, @RequestParam(value="categoryNo", defaultValue="0") String categoryNo, @RequestParam(value="page", defaultValue="1") int page, Model model, HttpServletRequest request) {
	   
	   HashMap<String, String> map1 = new HashMap<>();
	      System.out.printf("standard = %s", standard);
	      
	      map1.put("keyword", keyword);
	      map1.put("categoryNo", categoryNo);
	      map1.put("standard", standard);
	      
	      int listCount = pService.getListCount(map1);
	      int currentPage = page;
	      PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 12);
	      ArrayList<Product> pList = pService.selectProductMap(pi, map1);
	      ArrayList<Attachment> aList = pService.selectPhotoMap(map1);
	      ArrayList<Attachment> thList = pService.selectPhotoTHMap(map1);
	      ArrayList<Option> oList = pService.selectAllOption();
	      
	      for(int i=0; i<pList.size(); i++) {
	    	  for(int j=0; j<thList.size(); j++) {
	    		  if(pList.get(i).getProductNo()==thList.get(j).getBoardNo() && thList.get(j).getPhotoLevel()==0 && thList.get(j).getBoardType()==5) {
	    			  pList.get(i).setPhotoPath(thList.get(j).getPhotoPath());
	    		  }
	    	  }
	      }
	      
	      System.out.println(pList);
	      ArrayList<Review> rList = pService.selectproductAllReview();
	      HashMap<String, Object> map = new HashMap<>();
	      
	      if(aList != null) {
	    	 map.put("currentPage", currentPage);
	         map.put("thList", thList);
	         map.put("rList", rList);
	         map.put("categoryNo", categoryNo);
	         map.put("keyword", keyword);
	         map.put("standard", standard);
	         
	         map.put("aList", aList);
	         map.put("pList", pList);
	         map.put("pi", pi);
	         map.put("loc", request.getRequestURI());
	         return map;
	      }else {
	         throw new ProductException("상품 목록 조회 실패");
	      }
   }
   
   
   
   
   
   
   @GetMapping("productDetail.so")
   public String productDetailView(@RequestParam(value="reviewStatus", defaultValue="1") int reviewStatus, @RequestParam("productNo") int productNo, Model model) {
      Member loginUser = (Member)model.getAttribute("loginUser");
      Product p = pService.selectProductDetail(productNo);
      //상품사진
      ArrayList<Attachment> list = pService.selectPhotoDetail(productNo);
      ArrayList<Option> oList = pService.optionDetail(productNo);
      ArrayList<Qna> qList = pService.selectProductQna(productNo);
      
      ArrayList<Member> mList = pService.selectReviewMember(productNo);
      
      ArrayList<Review> rList = new ArrayList<>();
      
      
      if(reviewStatus==1) {
         rList = pService.selectProductReview(productNo);
      }else {
         rList = pService.selectProductPhotoReview(productNo);
      }
      
      //리뷰사진
      ArrayList<Attachment> aList = pService.selectPhotoReview(productNo);
      
      imageStorage.deleteImage("cb1d55f4-e2c4-42b1-96b9-948d8883b3c5.png","sohwa");
      
      
      int roundScore = (int) Math.round(p.getProductScore());
      
      model.addAttribute("loginUser", loginUser);
      model.addAttribute("roundScore", roundScore);
      model.addAttribute("reviewStatus", reviewStatus);
      model.addAttribute("mList", mList);
      model.addAttribute("aList", aList);
      model.addAttribute("rList", rList);
      model.addAttribute("qList", qList);
      model.addAttribute("productNo", productNo);
      model.addAttribute("oList", oList);
      model.addAttribute("list", list);
      model.addAttribute("p", p);
      return "views/sohwa/productDetail";
   }
   
   
   //상품등록
   @PostMapping("productEnroll.so")
   public String insertProduct(@ModelAttribute Product p, @RequestParam(value="option") String[] options, @RequestParam("productEno") Integer[] productEnos, @RequestParam("detailFile") ArrayList<MultipartFile> detailFiles, @RequestParam("coreFile") ArrayList<MultipartFile> coreFiles, Model model) {
      
      
      
      ArrayList<Attachment> coreList = new ArrayList<>();
      ArrayList<Attachment> detailList = new ArrayList<>();
      String name="sohwa";
       
      int result1 = 0;
        int result2 = 0;
        int result3 = 0;
        
        
        
      for(int i=0; i<coreFiles.size(); i++) {
         MultipartFile upload = coreFiles.get(i); //파일 하나씩 뽑아오기.
         String[] returnArr = imageStorage.saveImage(upload, name);
         
         
         
         if(returnArr != null) {
            
            Attachment a = new Attachment();
            if(i==0) {
               a.setPhotoRename(returnArr[0]);
               a.setPhotoPath(returnArr[1]);
               a.setPhotoLevel(0);
            }else {
               a.setPhotoRename(returnArr[0]);
               a.setPhotoPath(returnArr[1]);
               a.setPhotoLevel(1);
            }
            coreList.add(a);
         }
         
         
      }
      
      
      //rename 이랑 경로 뱉어냄.
      for(int i=0; i<detailFiles.size(); i++) {
         MultipartFile upload = detailFiles.get(i); //파일 하나씩 뽑아오기.
         String[] returnArr = imageStorage.saveImage(upload, name);
         
         if(returnArr != null) {
            Attachment a = new Attachment();
            a.setPhotoRename(returnArr[0]);
            a.setPhotoPath(returnArr[1]);
            a.setPhotoLevel(2);
            
            detailList.add(a);
         }
         
      }
      
      int eno = 0;
      
      for(int i=0; i<options.length; i++) {
         eno += productEnos[i];
         p.setEno(eno);
      }
      
      result1 = pService.insertProduct(p);
      
      //대표사진 저장
      for(Attachment a : coreList) {
         a.setBoardNo(p.getProductNo());
      }
      result2 = pService.insertProductPhoto(coreList);
      
      
      //상세사진 저장
      for(Attachment a : detailList) {
         a.setBoardNo(p.getProductNo());
      }
      result3 = pService.insertProductPhoto(detailList);
      
      
      
      
      
      
      
      ArrayList<Option> list = new ArrayList<>();
      
      for(int i=0; i<options.length; i++) {
         String option = options[i];
         Integer productEno = productEnos[i];
         
         
         
         Option o = new Option();
          o.setProductOptionColor(option);
          o.setProductOptionEno(productEno);
          o.setProductNo(p.getProductNo());
          list.add(o);
      }
      
      
      
      
      
      int result0 = pService.insertOption(list);
      
      
      System.out.println(list);
      //[Option(productOptionNo=0, productOptionColor=그린, productOptionEno=15, productNo=32), Option(productOptionNo=0, productOptionColor=주황, productOptionEno=20, productNo=32), Option(productOptionNo=0, productOptionColor=보라, productOptionEno=15, productNo=32)]
      
      
      
      
      if(result0 + result1 + result2 + result3 == options.length + coreList.size()+detailList.size()+1) {
         return "redirect:adminProductList.so";
      }else {
         throw new ProductException("상품 등록 실패");
      }
   }
   
   
   
   @GetMapping("refundOrder.so")
   public String refundOrder(@RequestParam("orderNo") int orderNo, Model model) {
	   String paymentKey = pService.selectPaymentKey(orderNo);
	   String token = "test_sk_24xLea5zVAjM2olw4v70rQAMYNwW:";
	   String base64EncodedToken = Base64.getEncoder().encodeToString(token.getBytes());
	   HttpRequest request = HttpRequest.newBuilder()
		    .uri(URI.create("https://api.tosspayments.com/v1/payments/" + paymentKey + "/cancel"))
		    .header("Authorization", "Basic " + base64EncodedToken)
		    .header("Content-Type", "application/json")
		    .method("POST", HttpRequest.BodyPublishers.ofString("{\"cancelReason\":\"고객이 취소를 원함\"}"))
		    .build();
		HttpResponse<String> response;
		
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	   int result = pService.refundOrder(orderNo);
	   
	   if(result > 0) {
		   return "redirect:myOrderPageView.so";
	   }else {
		   throw new ProductException("환불 요청 실패");
	   }
   }
   
   
   
   
   @GetMapping("insertCart.so")
      public String insertCart(@RequestParam("price") int price, @RequestParam("option") String option, @RequestParam("productNo") int productNo, @RequestParam("eno") int eno, Model model) {
         
         String id = ((Member)model.getAttribute("loginUser")).getMemberId();
         Cart c = new Cart();
         c.setProductNo(productNo);
         c.setProductEno(eno);
         c.setBuyOption(option);
         c.setMemberId(id);
         c.setBuyPrice(price);
         int result = pService.insertCart(c);
         
         if(result > 0) {
             return "redirect:productDetail.so?productNo=" + productNo;
         }else {
            throw new ProductException("장바구니 등록에 실패");
         }
         
    }
   
   @GetMapping("orderPage.so")
   public String insertOrder(@RequestParam("price") int price, @RequestParam("option") String option, @RequestParam("productNo") int productNo, @RequestParam("eno") int eno, Model model) {
      
      Member loginUser = (Member)model.getAttribute("loginUser");
      Payment pa = new Payment();
      pa.setOrderPrice(price);
      pa.setProductNo(productNo);
      pa.setOrderProductEno(eno);
      
      Product p = pService.selectProductDetail(productNo);
      ArrayList<Attachment> aList = pService.selectAllPhoto();
      
      model.addAttribute("option", option);
      model.addAttribute("aList", aList);
      model.addAttribute("loginUser", loginUser);
      model.addAttribute("p", p);
      model.addAttribute("eno", eno);
      return "views/sohwa/orderPage";
   }
   
   
   
   
   
   @GetMapping("goCartOrder.so")
   public String goCartOrder(Model model, @RequestParam("deliveryPrice") int deliveryPrice) {
      Member loginUser = (Member)model.getAttribute("loginUser");
      String id = loginUser.getMemberId();
      ArrayList<Cart> cList = pService.selectCart(id);
      ArrayList<Product> pList = pService.selectAllProduct();
      ArrayList<Attachment> aList = pService.selectAllPhoto();
      
      model.addAttribute("deliveryPrice", deliveryPrice);
      model.addAttribute("aList", aList);
      model.addAttribute("pList", pList);
      model.addAttribute("cList", cList);
      model.addAttribute("loginUser", loginUser);
      return "views/sohwa/orderPage2";
   }
   
   
   
   
   @GetMapping("cartView.so")
   public String cartView(Model model) {
      String id = ((Member)model.getAttribute("loginUser")).getMemberId();
      ArrayList<Cart> list = pService.memberCart(id);
       ArrayList<Product> pList = pService.selectAllProduct();
       ArrayList<Attachment> aList = pService.selectAllPhoto();
       ArrayList<Option> oList = pService.selectAllOption();
       
       model.addAttribute("oList", oList);
        model.addAttribute("aList", aList);
        model.addAttribute("pList", pList);
        model.addAttribute("list", list);
      return "views/sohwa/cartPage";
   }
   
   
   
   @GetMapping("deleteCart.so")
   public String deleteCart(@RequestParam("cartKeyNo") int cartKeyNo, Model model) {
      int result = pService.deleteCart(cartKeyNo);
      if(result > 0) {
         return "redirect:cartView.so";
      }else {
         throw new ProductException("삭제 실패");
      }
   }
   
   @PostMapping("deleteCarts.so")
   @ResponseBody
   public String deleteCarts(@RequestBody List<String> cartKeyNos) {
      System.out.println(cartKeyNos); //[309, 307]
      int result = pService.deleteCarts(cartKeyNos);
      
      if(result > 0) {
         return "success";
      }else {
         return "fail";
      }
   }
   
   
   
   @PostMapping("updateCartEno.so")
   @ResponseBody
   public String updateCartEno(@RequestParam("cartKeyNo") int cartKeyNo, @RequestParam("price") int price, @RequestParam("quantity") int quantity, Model model) {
      Cart c = new Cart();
      c.setCartKeyNo(cartKeyNo);
      c.setProductEno(quantity);
      c.setBuyPrice(price);
      System.out.println(price);
      int result = pService.updateCartEno(c);
      
      if(result > 0) {
         return "success";
      }else {
         return "fail";
      }
   }
   
   
   @GetMapping("centerView.so")
   public String qnaView(Model model) {
      Member loginUser = (Member)model.getAttribute("loginUser");
      
      ArrayList<Product> pList = pService.selectAllNProduct();
      ArrayList<Attachment> aList = pService.selectAllPhoto();
      
      
      
      if(loginUser != null) {
         String id = ((Member)model.getAttribute("loginUser")).getMemberId();
         ArrayList<Qna> qList = pService.selectMyQna(id);
         model.addAttribute("pList", pList);
         model.addAttribute("aList", aList);
         model.addAttribute("qList", qList);
         return "views/sohwa/centerPage";
      }else {
         return "views/sohwa/centerPage2";
      }
      
   }

   
   @PostMapping("insertQna.so")
   @ResponseBody
   public String insertQna(@RequestParam("productNo") int productNo, @RequestParam("questionContent") String questionContent, Model model) {
      String id = ((Member)model.getAttribute("loginUser")).getMemberId();
      Qna q = new Qna();
      q.setProductNo(productNo);
      q.setQuestionContent(questionContent);
      q.setMemberId(id);
      int result = pService.insertQna(q);
      
      if(result > 0) {
         return "success";
      }else {
         return "fail";
      }
   }
   
   
   
//   @GetMapping("answerY.so")
//   public String selectAnswerY(@RequestParam(value="page", defaultValue="1") int page, HttpServletRequest request, Model model){
//      
//      
//   }
   
   @PostMapping("insertAnswer.so")
   public String insertAnswer(@RequestParam("questionNo") int questionNo, @RequestParam("answerContent") String answerContent) {
      Qna q = new Qna();
      q.setAnswerContent(answerContent);
      q.setQuestionNo(questionNo);
      
      int result = pService.updateAnswer(q);
      
      if(result > 0) {
         return "redirect:adminQnaListView.so";
      }else {
         throw new ProductException("문의답글 등록 실패");
      }
   }
   
   
   @GetMapping("deleteQna.so")
   public String deleteQna(@RequestParam("questionNo") int questionNo, Model model) {
      int result = pService.deleteQna(questionNo);
      
      if(result > 0) {
         return "redirect:adminQnaListView.so?status=2";
      }else {
         throw new ProductException("문의 삭제 실패");
      }
      
      
   }
   
   @GetMapping("deleteQuestion.so")
   public String deleteQuestion(@RequestParam("questionNo") int questionNo, Model model){
      int result = pService.deleteQuestion(questionNo);
      if(result > 0) {
         return "redirect:centerView.so";
      }else {
         throw new ProductException("문의 삭제 실패");
      }
   }
   
   
   
   @PostMapping("enrollReview.so")
   public String insertReview(@ModelAttribute Review r, @RequestParam("file") MultipartFile upload, Model model) {
      
      String id = ((Member)model.getAttribute("loginUser")).getMemberId();
      r.setMemberId(id);
      String name="sohwa";
      String[] returnArr = imageStorage.saveImage(upload, name);
      Attachment a = new Attachment();
      int result1 = pService.insertReview(r);
      int result2 = 0;
      
      
      if(returnArr != null) {
         a.setPhotoRename(returnArr[0]);
         a.setPhotoPath(returnArr[1]);
         a.setPhotoLevel(0);
         a.setBoardNo(r.getReviewNo());
         result2 = pService.insertReviewPhoto(a);
      }
      
      System.out.println(r);  //Review(reviewNo=59, reviewContent=wagfwe, reviewUploadDate=null, reviewScore=4, reviewModifyDate=null, reviewStatus=null, productNo=111, memberId=ming11)
      
      int[] scoreArr = pService.selectAllProductScore(r.getProductNo());
      double avgScore = 0;
      double total = 0;
      for (int i = 0; i < scoreArr.length; i++) {
          total += scoreArr[i];
      }

      // 배열의 길이로 나누어 평균 계산
      avgScore = total / scoreArr.length;
      
      System.out.println(total);
      System.out.println(avgScore);
      
      Product p = new Product();
      p.setProductNo(r.getProductNo());
      p.setProductScore(avgScore);
      
      int result3 = pService.updateScore(p);
      
      if(result1 > 0) {
         return "redirect:productDetail.so?productNo=" + r.getProductNo();
      }else {
         throw new ProductException("리뷰 등록 실패");
      }
      
   }
   
   //리뷰삭제
   @GetMapping("deleteReview.so")
   public String deleteReview(@RequestParam("reviewNo") int reviewNo, @RequestParam("productNo") int productNo) {
      
      String name="sohwa";
      int result = pService.deleteReview(reviewNo);
      String deleteRename = pService.deleteSelectReview(reviewNo);
      
      if(deleteRename != null) {
    	  Boolean sf = imageStorage.deleteImage(deleteRename, name);
      }
      
      
      if(result > 0) {
         return "redirect:productDetail.so?productNo=" + productNo;
      }else {
         throw new ProductException("리뷰 삭제 실패");
      }
   }
   
   
   @PostMapping("updateReview.so")
   public String updateReview(@RequestParam(value="deleteRename", defaultValue="") String deleteRename, @RequestParam("reviewContent") String reviewContent, @RequestParam("updateFile") MultipartFile updateFile, @RequestParam("productNo") int productNo, @RequestParam("updateReviewScore") int reviewScore, @RequestParam("reviewNo") int reviewNo) {
      
      Review r = new Review();
      Attachment a = new Attachment();
      String name="sohwa";
      r.setProductNo(productNo);
      r.setReviewContent(reviewContent);
      r.setReviewNo(reviewNo);
      r.setReviewScore(reviewScore);
      
      System.out.println(reviewScore);
      System.out.println(reviewContent);
      System.out.println(reviewNo);
      System.out.println(updateFile.getOriginalFilename());
      
      int result1 = 0;
      int result2 = 0;
      //deleteRename도 비어있고, updateFile이 비어있을 때 (사진 X)
      //deleteRename은 비어있고, updateFile이 있을 때 (새로 추가)
      //deleteRename 차있고, updateFile이 비어있을 때 (사진 그대로 유지)
      //deleteRename 차있고, updateFile도 차있을 때 (사진 변경)
      
      //deleteRename도 비어있고, updateFile이 비어있을 때 (사진 X)
      if(deleteRename.equals("")) {
         if(updateFile.getOriginalFilename().equals("")) {
            result1 = pService.updateReviewInfo(r);
            
         }else if(!updateFile.getOriginalFilename().equals("")) {
            //deleteRename은 비어있고, updateFile이 있을 때 (새로 추가)
            a.setBoardNo(reviewNo);
            a.setBoardType(6);
            a.setPhotoLevel(0);
            String[] returnArr = imageStorage.saveImage(updateFile, name);
            
            if(returnArr != null) {
               a.setPhotoPath(returnArr[1]);
               a.setPhotoRename(returnArr[0]);
               
               System.out.println(a);
               result2 = pService.updateReviewPhoto(a);
            }
            result1 = pService.updateReviewInfo(r);
         }
      }else {
         //deleteRename 차있고, updateFile이 비어있을 때 (사진 그대로 유지)
         if(updateFile.getOriginalFilename().equals("")) {
            result1 = pService.updateReviewInfo(r);
         }else {
            //deleteRename 차있고, updateFile도 차있을 때 (사진 변경)
            Boolean sf = imageStorage.deleteImage(deleteRename, name);
            System.out.println("sf:" + sf);
            pService.deleteReviewPhoto(reviewNo);
            a.setBoardNo(reviewNo);
            a.setBoardType(6);
            a.setPhotoLevel(0);
            String[] returnArr = imageStorage.saveImage(updateFile, name);
            
            if(returnArr != null) {
               a.setPhotoPath(returnArr[1]);
               a.setPhotoRename(returnArr[0]);
               
               System.out.println(a);
               result2 = pService.updateReviewPhoto(a);
            }
            result1 = pService.updateReviewInfo(r);
         }
         
         
         
      }
      
      
      int[] scoreArr = pService.selectAllProductScore(r.getProductNo());
      double avgScore = 0;
      double total = 0;
      for (int i = 0; i < scoreArr.length; i++) {
          total += scoreArr[i];
      }

      // 배열의 길이로 나누어 평균 계산
      avgScore = total / scoreArr.length;
      
      Product p = new Product();
      p.setProductNo(r.getProductNo());
      p.setProductScore(avgScore);
      
      int result3 = pService.updateScore(p);
      
      
      
      
      
      return "redirect:productDetail.so?productNo=" + productNo;
      
   }
   
   
   
   
   //상품삭제
   @GetMapping("updateStatus.so")
   @ResponseBody
   public String deleteList(@RequestParam("checked[]") ArrayList<String> checkBoxArr) {
      String name="sohwa";
       
       int result = pService.updateYProduct(checkBoxArr);
       int result2 = pService.updateYOption(checkBoxArr);
       int result3 = pService.updateYCart(checkBoxArr);
       int result4 = pService.updateYQna(checkBoxArr);
       int result5 = pService.updateYReview(checkBoxArr);
       ArrayList<String> delRenames = pService.selectYPhoto(checkBoxArr);
       
       for(String delRename:delRenames) {
          Boolean sf = imageStorage.deleteImage(delRename, name);
          System.out.println(delRename);
          pService.updateYPhoto(checkBoxArr);
          pService.deleteCarts(checkBoxArr);
          System.out.println(sf);
       }
       
       if(result > 0){
          return "성공";
       }else {
          return "실패";
       }
       
   }
   
//   @GetMapping("insertPayment.so")
//   public ResponseEntity<Object> insertPayment(@RequestParam("orderId") String orderId, @RequestParam("paymentKey") String paymentKey, @RequestParam("amount") int amount) {
//      
//      
//      // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
//       // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
//       JSONParser parser = new JSONParser();
//       byte[] encodedBytes;
//       String authorizations;
//      
//      JSONObject obj = new JSONObject();
//      obj.put("orderId", orderId);
//      obj.put("amount", amount);
//      obj.put("paymentKey", paymentKey);
//      
//      String widgetSecretKey = "test_sk_24xLea5zVAjM2olw4v70rQAMYNwW";
//      Base64.Encoder encoder = Base64.getEncoder();
//      int code = 0;
//      JSONObject jsonObject = null;
//      try {
//         encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes("UTF-8"));
//         
//         authorizations = "Basic " + new String(encodedBytes, 0, encodedBytes.length);
//         // 결제를 승인하면 결제수단에서 금액이 차감돼요.
//         URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
//         HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//         connection.setRequestProperty("Authorization", authorizations);
//         connection.setRequestProperty("Content-Type", "application/json");
//         connection.setRequestMethod("POST");
//         connection.setDoOutput(true);
//         
//         OutputStream outputStream = connection.getOutputStream();
//         outputStream.write(obj.toString().getBytes("UTF-8"));
//         
//         code = connection.getResponseCode();
//         boolean isSuccess = code == 200 ? true : false;
//         
//         InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();
//         
//         // 결제 성공 및 실패 비즈니스 로직을 구현하세요.
//         Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
//         jsonObject = (JSONObject) parser.parse(reader);
//         responseStream.close();
//      } catch (UnsupportedEncodingException e) {
//         e.printStackTrace();
//      } catch (MalformedURLException e) {
//         e.printStackTrace();
//      } catch (ProtocolException e) {
//         e.printStackTrace();
//      } catch (IOException e) {
//         e.printStackTrace();
//      } catch (ParseException e) {
//         e.printStackTrace();
//      }
//      
//      return ResponseEntity.status(code).body(jsonObject);
//   }
   
   @GetMapping("myOrderPageView.so")
   public String myOrderPageView(Model model){
	   String id = ((Member)model.getAttribute("loginUser")).getMemberId();
	   ArrayList<Payment> paList = pService.selectMyOrder(id);
	   ArrayList<Integer> orderNos = pService.selectOrderNo(id);
	   ArrayList<Product> pList = pService.selectAllProduct();
	   ArrayList<Attachment> aList = pService.selectAllPhoto();
	   
	   
	   
	   model.addAttribute("pList", pList);
	   model.addAttribute("aList", aList);
	   model.addAttribute("orderNos", orderNos);
	   model.addAttribute("paList", paList);
      return "views/sohwa/myOrderPage";
   }
   
   
   @GetMapping("deleteOrder.so")
   public String deleteOrder(@RequestParam("orderNo") int orderNo) {
	   int result = pService.deleteOrder(orderNo);
	   
	   if(result > 0) {
		   return "redirect:myOrderPageView.so";
	   }else {
		   throw new ProductException("주문내역삭제 실패");
	   }
	   
   }
   
   
   @GetMapping("detailOrderView.so")
   public String detailOrderView(@RequestParam("orderNo") int orderNo, Model model) {
	   
	   //order_status = 'Y'인 (환불요청)까지 다 찾아옴.
	   ArrayList<Payment> paList = pService.selectPayment(orderNo);
	   ArrayList<Product> pList = pService.selectAllProduct();
	   ArrayList<Attachment> aList = pService.selectAllPhoto();
	   Payment buyerInfo = pService.selectBuyerInfo(orderNo);
	   
	   model.addAttribute("buyerInfo", buyerInfo);
	   model.addAttribute("aList", aList);
	   model.addAttribute("pList", pList);
	   model.addAttribute("paList", paList);
	   return "views/sohwa/myOrderDetail";
   }
   
   
   @GetMapping("insertPayment.so")
    public String success(Model model, @RequestParam("required") String required, @RequestParam("name") String name, @RequestParam("phone") String phone, @RequestParam("orderId") String orderId,@RequestParam("options") String options, @RequestParam("paymentKey") String paymentKey, @RequestParam("amount") String amount, @RequestParam("productNos") String productNos, @RequestParam("enos") String enos, @RequestParam("address") String address) {
       System.out.println(orderId);
       System.out.println(paymentKey);
       System.out.println(amount); //100
       String id = ((Member)model.getAttribute("loginUser")).getMemberId();
       //{"mId":"tvivarepublica","lastTransactionKey":"34292C37FAF8374CFEEB0EEDE9E5EEE4","paymentKey":"k0A2Ga1QqXjExPeJWYVQOqkPjmjBdq849R5gvNLdzZwO6oKl","orderId":"555324","orderName":"[네이처하이크] 더블 후드 슬리핑백 침낭외 2건","taxExemptionAmount":0,"status":"DONE","requestedAt":"2024-01-19T09:37:40+09:00","approvedAt":"2024-01-19T09:37:58+09:00","useEscrow":false,"cultureExpense":false,"card":null,"virtualAccount":null,"transfer":null,"mobilePhone":null,"giftCertificate":null,"cashReceipt":null,"cashReceipts":null,"discount":null,"cancels":null,"secret":"ps_d46qopOB89ddnydPaq75rZmM75y0","type":"NORMAL","easyPay":{"provider":"토스페이","amount":196100,"discountAmount":0},"country":"KR","failure":null,"isPartialCancelable":true,"receipt":{"url":"https://dashboard.tosspayments.com/receipt/redirection?transactionId=tviva20240119093740OT7W4&ref=PX"},"checkout":{"url":"https://api.tosspayments.com/v1/payments/k0A2Ga1QqXjExPeJWYVQOqkPjmjBdq849R5gvNLdzZwO6oKl/checkout"},"currency":"KRW","totalAmount":196100,"balanceAmount":196100,"suppliedAmount":178273,"vat":17827,"taxFreeAmount":0,"method":"간편결제","version":"2022-11-16"}//{"mId":"tvivarepublica","lastTransactionKey":"FF4400ED2C256723D7B4568D1CFE20FE","paymentKey":"01OAv2P6yqLlDJaYngro9XZ27RO97G3ezGdRpXxKN7BQMEk4","orderId":"309629","orderName":"NaN건","taxExemptionAmount":0,"status":"DONE","requestedAt":"2024-01-19T09:30:56+09:00","approvedAt":"2024-01-19T09:31:12+09:00","useEscrow":false,"cultureExpense":false,"card":null,"virtualAccount":null,"transfer":null,"mobilePhone":null,"giftCertificate":null,"cashReceipt":null,"cashReceipts":null,"discount":null,"cancels":null,"secret":"ps_GePWvyJnrKvEdbYXXbXLVgLzN97E","type":"NORMAL","easyPay":{"provider":"토스페이","amount":196100,"discountAmount":0},"country":"KR","failure":null,"isPartialCancelable":true,"receipt":{"url":"https://dashboard.tosspayments.com/receipt/redirection?transactionId=tviva20240119093056MY3M0&ref=PX"},"checkout":{"url":"https://api.tosspayments.com/v1/payments/01OAv2P6yqLlDJaYngro9XZ27RO97G3ezGdRpXxKN7BQMEk4/checkout"},"currency":"KRW","totalAmount":196100,"balanceAmount":196100,"suppliedAmount":178273,"vat":17827,"taxFreeAmount":0,"method":"간편결제","version":"2022-11-16"}
       System.out.println(productNos);
       HttpRequest request = HttpRequest.newBuilder()
             .uri(URI.create("https://api.tosspayments.com/v1/payments/confirm"))
             .header("Authorization", "Basic dGVzdF9za18yNHhMZWE1elZBak0yb2x3NHY3MHJRQU1ZTndXOg==")
             .header("Content-Type", "application/json")
             .method("POST", HttpRequest.BodyPublishers.ofString("{\"paymentKey\":\"" + paymentKey + 
                                                    "\",\"orderId\":\"" + orderId + 
                                                    "\",\"amount\":" + amount + "}"))
             .build();
      HttpResponse<String> response = null;
      
      
      
      // 쉼표로 나누기
        String[] productArray = productNos.split(",");
        String[] enoArray = enos.split(",");
        String[] optionArray = options.split(",");
        // ArrayList 생성
        ArrayList<Payment> paList = new ArrayList<>();

       // 나눠진 값을 Payment 객체에 할당하여 ArrayList에 추가
        for (int i = 0; i < productArray.length; i++) {
            Payment pa = new Payment(); // trim()을 사용하여 공백 제거
            pa.setProductOption(optionArray[i].trim());
            pa.setProductNo(Integer.parseInt(productArray[i].trim()));
            pa.setOrderProductEno(Integer.parseInt(enoArray[i].trim()));
            pa.setOrderAddress(address);
            pa.setDeliveryStatus("결제완료");
            pa.setMemberId(id);
            pa.setOrderPrice(Integer.parseInt(amount));
            pa.setOrderPhone(phone);
            pa.setOrderName(name);
            pa.setOrderContent(required);
            pa.setOrderNo(Integer.parseInt(orderId));
            pa.setPaymentKey(paymentKey);
            paList.add(pa);
            
        }
      System.out.println(paList);
      //[Payment(orderKeyNo=0, orderPrice=38600, orderDate=null, orderContent=test2, orderNo=320746, orderMethod=null, orderCard=null, orderStatus=null, memberId=ming11, orderAddress=12066@경기 남양주시 진접읍 해밀예당1로 40@1층 LG유플러스, orderProductEno=1, deliveryStatus=결제완료, productOption=하양, productNo=202, orderPhone=01073558749, orderName=민경), 
      //Payment(orderKeyNo=0, orderPrice=38600, orderDate=null, orderContent=test2, orderNo=320746, orderMethod=null, orderCard=null, orderStatus=null, memberId=ming11, orderAddress=12066@경기 남양주시 진접읍 해밀예당1로 40@1층 LG유플러스, orderProductEno=3, deliveryStatus=결제완료, productOption=레드, productNo=201, orderPhone=01073558749, orderName=민경)]
      
      
      int result = pService.insertPayment(paList);
      try {
         response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
         System.out.println(response.body());
      } catch (IOException e) {
         e.printStackTrace();
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
      model.addAttribute("orderNo", orderId);
      model.addAttribute("address", address.split("@")[1] + " " + address.split("@")[2]);
      model.addAttribute("phone", phone);
      model.addAttribute("name", name);
      model.addAttribute("price", amount);
      return "views/sohwa/success";
    }
    
     
   
   
   @GetMapping("adminOrderListView.so")
   public String adminOrderListView(@RequestParam(value="keyword", defaultValue="") String keyword, @RequestParam(value="searchType", defaultValue="0") String searchType, @RequestParam(value="page", defaultValue="1") int page,Model model, @RequestParam(value="status", defaultValue="1") int status, HttpServletRequest request) {
	   ArrayList<Payment> paList = new ArrayList<>();
	   int listCount = 0;
	   PageInfo pi = new PageInfo();
	   if(status == 1) {
		   HashMap<String, String> map = new HashMap<>();
	       map.put("keyword", keyword);
	       map.put("searchType", searchType);
	       int currentPage = page;
	       listCount = pService.getListCountOrder(map);
	       pi = Pagination.getPageInfo(currentPage, listCount, 10);
		   paList = pService.selectAllPayment(map, pi);
	   }else {
		   HashMap<String, String> map = new HashMap<>();
	       map.put("keyword", keyword);
	       map.put("searchType", searchType);
	       int currentPage = page;
	       listCount = pService.getListCountOrderN(map);
	       pi = Pagination.getPageInfo(currentPage, listCount, 10);
		   paList = pService.selectNPayment(map,pi);
	   }
	   
	   model.addAttribute("loc", request.getRequestURI());
	   model.addAttribute("keyword", keyword);
	   model.addAttribute("searchType", searchType);
	   model.addAttribute("pi", pi);
	   model.addAttribute("status", status);
	   model.addAttribute("paList", paList);
	   return "views/sohwa/(admin)orderList";
   }
   
   
   
   
   @GetMapping("adminOrderDetail.so")
   public String adminOrderDetail(@RequestParam("orderKeyNo") int orderKeyNo, Model model) {
	   Payment pa = pService.selectOrderDetail(orderKeyNo);
	   ArrayList<Attachment> aList = pService.selectAllPhoto();
	   ArrayList<Product> pList = pService.selectAllProduct();
	   model.addAttribute("pList", pList);
	   model.addAttribute("aList", aList);
	   model.addAttribute("pa", pa);
	   return "views/sohwa/(admin)orderDetail";
   }
   
   
   
   @PostMapping("productUpdate.so")
   public String updateAttm(@RequestParam(value="option") String[] options, @RequestParam("productEno") Integer[] productEnos, @ModelAttribute Product p, @RequestParam("deleteAttm") String[] deleteAttm, @RequestParam("coreFile") ArrayList<MultipartFile> coreFiles, @RequestParam("detailFile") ArrayList<MultipartFile> detailFiles) {
      
      
      //product 정보
      System.out.println(p);
      int result = pService.updateProduct(p);
      System.out.println(result);
      
      //옵션 삭제하고 다시 insert처리
      ArrayList<Attachment> coreList = new ArrayList<>();
      ArrayList<Attachment> detailList = new ArrayList<>();
      String name = "sohwa";
      int coreResult = 0;
      int detailResult = 0;
      int deleteResult = 0;
      
      System.out.println("deleteAttm:" +deleteAttm);
      int deleteOptionResult = pService.deleteOption(p.getProductNo());
      
      
      ArrayList<Option> oList = new ArrayList<>();
            
         for(int i=0; i<options.length; i++) {
            String option = options[i];
            Integer productEno = productEnos[i];
            
            
            
            Option o = new Option();
             o.setProductOptionColor(option);
             o.setProductOptionEno(productEno);
             o.setProductNo(p.getProductNo());
             oList.add(o);
         }
         
      int optionResult = pService.insertOption(oList);
      
      
      //deleteAttm이 비워져있고, coreFiles와 detailFiles 둘 다 존재
      //deleteAttm이 채워져있고, coreFiles와 detailFiles 둘 다 존재
            
      
      for(int i=0; i<coreFiles.size(); i++) {
         MultipartFile coreUpload = coreFiles.get(i);
         
         if(!coreUpload.getOriginalFilename().equals("")) {
            String[] returnArr1 = imageStorage.saveImage(coreUpload, name);
            
            if(returnArr1 != null) {
               Attachment a = new Attachment();
               a.setPhotoRename(returnArr1[0]);
               a.setPhotoPath(returnArr1[1]);
               a.setBoardType(5);
               a.setBoardNo(p.getProductNo());
               
               coreList.add(a);
            }
         }
         
      }
            
      
      ArrayList<String> delRename = new ArrayList<>();
      ArrayList<Integer> delLevel = new ArrayList<>();
      //deleteAttm이 비워져있고, coreFiles와 detailFiles도 비워져있을 때
      for(int i=0; i<deleteAttm.length; i++) {
         String rename = deleteAttm[i];
         if(!rename.equals("none")) {
            String[] split = rename.split("/");
            delLevel.add(Integer.parseInt(split[1]));
            delRename.add(split[0]);
          }
       }
      
      
      //이전의 첨부파일이 존재했는지에 대한 boolean값
      boolean existBeforeAttm = true;
      int deleteAttmResult = 0;
      int updateBoardResult = 0;
      int levelResult = 0;
      //하나라도 삭제하기로 했다.
      if(!delRename.isEmpty()) {
         deleteAttmResult = pService.deleteProductPhoto(delRename);
         if(deleteAttmResult > 0) {
            for(String rename : delRename) {
               //resources에 있는 uploadFiles에서도 삭제하겠다는 코드
               imageStorage.deleteImage(rename, name);
            }
         }
         
         
         //기존 파일 다 삭제했을 때
         if(delRename.size() == deleteAttm.length) {
            existBeforeAttm = false;
         }else {
            //기존 파일이 하나라도 남아있을 때
            for(int level : delLevel) {
               if(level == 0) {
                  //삭제할 객체의 레벨들 중에서 level이 0인 것이 하나라도 있다면 썸네일 없어진 것..다시 설정해줘야함.
                  //남아있는 사진들 중에서 가장 boardId가 빠른 것 썸네일로 지정해줄 것(level 0)
                  //return 해줄게 없음.
                  levelResult = pService.updatePhotoLevel(p.getProductNo());
                  break;
               }
            }
         }
      }
      
      for(int i=0; i<coreList.size(); i++) {
         Attachment a = coreList.get(i);
         a.setBoardNo(p.getProductNo());
         
         if(levelResult != 0) {
            //'원래 파일들이 하나라도 남아있다면'이라는 if 문
            //따라서 잔존하던 파일들 사이에서 어차피 썸네일 정해질 것이므로
            //어차피 추가하는 파일들은 썸네일이 될 가능성이 없다. 따라서, 다 1로 설정 가능
            a.setPhotoLevel(1);
         }else{
            if(i == 0) {
               a.setPhotoLevel(0);
            }else {
               a.setPhotoLevel(1);
            }
         }
      }
      
      
      
      for(int i=0; i<detailFiles.size(); i++) {
         MultipartFile detailUpload = detailFiles.get(i);
         
         if(!detailUpload.getOriginalFilename().equals("")) {
            String[] returnArr2 = imageStorage.saveImage(detailUpload, name);
            
            if(returnArr2 != null) {
               Attachment a = new Attachment();
               a.setPhotoRename(returnArr2[0]);
               a.setPhotoPath(returnArr2[1]);
               a.setPhotoLevel(2);
               a.setBoardType(5);
               a.setBoardNo(p.getProductNo());
               
               detailList.add(a);
            }
         }
      }
      
      if(!coreList.isEmpty()) {
         coreResult = pService.insertProductPhoto(coreList);
      }
      if(!detailList.isEmpty()) {
         detailResult = pService.insertProductPhoto(detailList);
      }
      
      if(optionResult > 0) {
         return "redirect:adminProductList.so";
      }else {
         throw new ProductException("상품수정실패");
      }
   }
   
   
   @GetMapping("updateDeliveryStatus.so")
   public String updateDeliveryStatus(@RequestParam("orderKeyNo") int orderKeyNo, @RequestParam("orderNo") String orderNo, @RequestParam("deliveryStatus") String deliveryStatus, Model model) {
	   HashMap<String, String> map = new HashMap<>();
	   map.put("orderNo", orderNo);
	   map.put("deliveryStatus", deliveryStatus);
	   int result = pService.updateDeliveryStatus(map);
	   
	   if(result > 0) {
		   return "redirect:adminOrderDetail.so?orderKeyNo=" + orderKeyNo;
	   }else {
		   throw new ProductException("배송 상태 변경 실패");
	   }
   }
   
   
   
   @GetMapping("purchaseYN.so")
   @ResponseBody
   public String purchaseYN(Model model, @RequestParam("productNo") int productNo) {
	   String id = ((Member)model.getAttribute("loginUser")).getMemberId();
	   Payment p = new Payment();
	   p.setMemberId(id);
	   p.setProductNo(productNo);
	   int result = pService.purchaseYN(p);
	   
	   if(result == 0) {
		   return "no";
	   }else {
		   return "yes";
	   }
	  
   }
   
   
   @GetMapping("sohwa.so")
   public String test() {
	   return "views/sohwa/sohwa";
   }
   
   
   
   
   
   
   
   
   
   
   
   
}