package com.product.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.product.dao.OrderDAO;
import com.product.dao.ProductDAO;
import com.product.dao.Utils;
import com.product.entity.Product;
import com.product.model.CartInfo;
import com.product.model.CustomerInfo;
import com.product.model.OrderDetailInfo;
import com.product.model.OrderInfo;
import com.product.model.PaginationResult;
import com.product.model.ProductInfo;
import com.product.service.ProductService;

@Controller
@Transactional
@EnableWebMvc
public class AdminController {
	
	
	 @Autowired
	   ProductService productService;	
	 
	 
	  @Autowired
	    private OrderDAO orderDAO;
	  
	  
	 
	 @InitBinder
	    public void initBinder(WebDataBinder binder) {
	        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
	        sdf.setLenient(true);
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	    }
	
	 @RequestMapping("/")
	    public String home() {
	        return "index";
	    }
	 
	// GET: Show Login Page
		@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
		public String login(Model model) {

			return "login";
		}

	// get: Account information
		@RequestMapping(value = { "/accountInfo" }, method = RequestMethod.GET)
		public String accountInfo(Model model) {

			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			System.out.println(userDetails.getPassword());
			System.out.println(userDetails.getUsername());
			System.out.println(userDetails.isEnabled());

			model.addAttribute("userDetails", userDetails);
			return "accountInfo";
		}
	 
	 // Get Product List
	 @RequestMapping({ "/productList" })
	    public String listProduct(Model model){	            
		List<Product> products = productService.getProducts();
		model.addAttribute("products" , products);	       
	        return "productList";
	    } 
	 
	 @RequestMapping({ "/addProduct" })
	    public String addProduct(Model theModel ){	            
		Product product = new Product();	       
		theModel.addAttribute("product", product);
	        return "productForm";
	    } 
	 
	 @RequestMapping({ "/saveProduct" })
	    public String saveProduct(@ModelAttribute("product") Product product ){	
		productService.addOrUpdateProduct(product);		
	    return "redirect:/productList";
	    } 


	 @RequestMapping({ "editProduct" })
	    public String editProduct(@RequestParam("code") String code, Model theModel ){	            
		Product product = productService.getProduct(code);       
		theModel.addAttribute("product", product);
	        return "productForm";
	    } 
	 
	 @RequestMapping({ "deleteProduct" })
	    public String deleteProduct(@RequestParam("code") String code ){		      
		productService.deleteProduct(code);		
		 return "redirect:/productList";
	    } 
	 
	 
	 @RequestMapping({ "/buyProduct" })
	    public String listProductHandler(HttpServletRequest request, Model model, //
	            @RequestParam(value = "code", defaultValue = "") String code) {
		 	

	        Product product = null;
	        
	        if (code != null && code.length() > 0) {
	           
	        	product = productService.getProduct(code); 
	        }
	        if (product != null) {
	 
	            // Cart info stored in Session.
	            CartInfo cartInfo = Utils.getCartInSession(request);
	 
	            ProductInfo productInfo = new ProductInfo(product);
	 
	            cartInfo.addProduct(productInfo, 1);
	        }
	        // Redirect to shoppingCart page.
	        return "redirect:/shoppingCart";
	    }
	 
	 
	 @RequestMapping({ "/shoppingCartRemoveProduct" })
	    public String removeProductHandler(HttpServletRequest request, Model model, //
	            @RequestParam(value = "code", defaultValue = "") String code) {
	        Product product = null;
	        if (code != null && code.length() > 0) {
	           product = productService.getProduct(code); 
	        }
	        if (product != null) {
	 
	            // Cart Info stored in Session.
	            CartInfo cartInfo = Utils.getCartInSession(request);
	 
	            ProductInfo productInfo = new ProductInfo(product);
	 
	            cartInfo.removeProduct(productInfo);
	 
	        }
	        // Redirect to shoppingCart page.
	        return "redirect:/shoppingCart";
	    }
	 
	 @RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.POST)
	    public String shoppingCartUpdateQty(HttpServletRequest request, //
	            Model model, //
	            @ModelAttribute("cartForm") CartInfo cartForm) {
	 
	        CartInfo cartInfo = Utils.getCartInSession(request);
	        cartInfo.updateQuantity(cartForm);
	 
	        // Redirect to shoppingCart page.
	        return "redirect:/shoppingCart";
	    }
	 
	    // GET: Show Cart
	    @RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.GET)
	    public String shoppingCartHandler(HttpServletRequest request, Model model) {
	        CartInfo myCart = Utils.getCartInSession(request);
	 
	        model.addAttribute("cartForm", myCart);
	        return "shoppingCart";
	    }
	 
	    
	    // GET: Enter customer information.
	    @RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.GET)
	    public String shoppingCartCustomerForm(HttpServletRequest request, Model model) {
	 
	        CartInfo cartInfo = Utils.getCartInSession(request);
	      
	        // Cart is empty.
	        if (cartInfo.isEmpty()) {
	             
	            // Redirect to shoppingCart page.
	            return "redirect:/shoppingCart";
	        }
	 
	        CustomerInfo customerInfo = cartInfo.getCustomerInfo();
	        if (customerInfo == null) {
	            customerInfo = new CustomerInfo();
	        }
	 
	        model.addAttribute("customerForm", customerInfo);
	 
	        return "shoppingCartCustomer";
	    }
	 
	    // POST: Save customer information.
	    @RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.POST)
	    public String shoppingCartCustomerSave(HttpServletRequest request, @ModelAttribute("customerForm") CustomerInfo customerForm) {
	  
	        // If has Errors.
	      /*  if (result.hasErrors()) {
	            customerForm.setValid(false);
	            // Forward to reenter customer info.
	            return "shoppingCartCustomer";
	        }*/
	 
	        customerForm.setValid(true);
	        CartInfo cartInfo = Utils.getCartInSession(request);
	 
	        cartInfo.setCustomerInfo(customerForm);
	 
	        // Redirect to Confirmation page.
	        return "redirect:/shoppingCartConfirmation";
	    }
	 
	    // GET: Review Cart to confirm.
	    @RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.GET)
	    public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
	        CartInfo cartInfo = Utils.getCartInSession(request);
	 
	        // Cart have no products.
	        if (cartInfo.isEmpty()) {
	            // Redirect to shoppingCart page.
	            return "redirect:/shoppingCart";
	        } else if (!cartInfo.isValidCustomer()) {
	            // Enter customer info.
	            return "redirect:/shoppingCartCustomer";
	        }
	 
	        return "shoppingCartConfirmation";
	    }
	 
	    // POST: Send Cart (Save).
	    @RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.POST)
	    // Avoid UnexpectedRollbackException (See more explanations)
	    @Transactional(propagation = Propagation.NEVER)
	    public String shoppingCartConfirmationSave(HttpServletRequest request, Model model) {
	        CartInfo cartInfo = Utils.getCartInSession(request);
	 
	        // Cart have no products.
	        if (cartInfo.isEmpty()) {
	            // Redirect to shoppingCart page.
	            return "redirect:/shoppingCart";
	        } else if (!cartInfo.isValidCustomer()) {
	            // Enter customer info.
	            return "redirect:/shoppingCartCustomer";
	        }
	        try {
	            orderDAO.saveOrder(cartInfo);
	        } catch (Exception e) {
	            // Need: Propagation.NEVER?
	            return "shoppingCartConfirmation";
	        }
	        // Remove Cart In Session.
	        Utils.removeCartInSession(request);
	         
	        // Store Last ordered cart to Session.
	        Utils.storeLastOrderedCartInSession(request, cartInfo);
	 
	        // Redirect to successful page.
	        return "redirect:/shoppingCartFinalize";
	    }
	 
	    @RequestMapping(value = { "/shoppingCartFinalize" }, method = RequestMethod.GET)
	    public String shoppingCartFinalize(HttpServletRequest request, Model model) {
	 
	        CartInfo lastOrderedCart = Utils.getLastOrderedCartInSession(request);
	 
	        if (lastOrderedCart == null) {
	            return "redirect:/shoppingCart";
	        }
	 
	        return "shoppingCartFinalize";
	    }
	    
	    
	    @RequestMapping(value = { "/orderList" }, method = RequestMethod.GET)
	    public String orderList(Model model, //
	            @RequestParam(value = "page", defaultValue = "1") String pageStr) {
	        int page = 1;
	        try {
	            page = Integer.parseInt(pageStr);
	        } catch (Exception e) {
	        }
	        final int MAX_RESULT = 5;
	        final int MAX_NAVIGATION_PAGE = 10;
	 
	        PaginationResult<OrderInfo> paginationResult //
	        = orderDAO.listOrderInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);
	 
	        model.addAttribute("paginationResult", paginationResult);
	        return "orderList";
	    }
	    
	    @RequestMapping(value = { "/order" }, method = RequestMethod.GET)
	    public String orderView(Model model, @RequestParam("orderId") String orderId) {
	        OrderInfo orderInfo = null;
	        if (orderId != null) {
	            orderInfo = this.orderDAO.getOrderInfo(orderId);
	        }
	        if (orderInfo == null) {
	            return "redirect:/orderList";
	        }
	        List<OrderDetailInfo> details = this.orderDAO.listOrderDetailInfos(orderId);
	        orderInfo.setDetails(details);
	 
	        model.addAttribute("orderInfo", orderInfo);
	 
	        return "order";
	    }
	    
}
