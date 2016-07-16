package com.spring.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.models.Product;
import com.spring.daos.ProductDAO;
import com.spring.daos.CategoryDAO;

@Controller
@RequestMapping("/product")
@Transactional
public class ProductController {

   @Autowired
   private ProductDAO productDAO;
   @Autowired
   private CategoryDAO categoryDAO;

   @RequestMapping("/form")
   public ModelAndView form(Product product) {
      ModelAndView modelAndView = new ModelAndView("product/form-add");
      return loadFormDependencies(modelAndView);

   }

   private ModelAndView loadFormDependencies(ModelAndView modelAndView) {
      modelAndView.addObject("categoryList", categoryDAO.all());
      return modelAndView;
   }

   @RequestMapping(method = RequestMethod.POST)
   public ModelAndView save(@Valid Product product, BindingResult bindingResult) {
      if (bindingResult.hasErrors()) {
         return form(product);
      }
      productDAO.save(product);
      return new ModelAndView("redirect:/product");
   }

   @RequestMapping(method = RequestMethod.GET, value = "/{id}")
   public ModelAndView load(@PathVariable("id") Integer id) {
      ModelAndView modelAndView = new ModelAndView("product/form-update");
      modelAndView.addObject("product", productDAO.findById(id));
      loadFormDependencies(modelAndView);
      return modelAndView;
   }

   @RequestMapping(method = RequestMethod.GET)
   public ModelAndView list(@RequestParam(defaultValue = "0", required = false) int page) {
      ModelAndView modelAndView = new ModelAndView("product/list");
      modelAndView.addObject("paginatedList", productDAO.paginated(page, 10));
      return modelAndView;
   }

   // apenas porque GET é mais fácil aqui. Seja meu convidado, se você deseja alterar.
   @RequestMapping(method = RequestMethod.GET, value = "/remove/{id}")
   public String remove(@PathVariable("id") Integer id) {
      Product product = productDAO.findById(id);
      productDAO.remove(product);
      return "redirect:/product";
   }

   @RequestMapping(method = RequestMethod.POST, value = "/{id}")
   public ModelAndView update(@PathVariable("id") Integer id, @Valid Product product, BindingResult bindingResult) {
      product.setId(id);
      if (bindingResult.hasErrors()) {
         return loadFormDependencies(new ModelAndView("product/form-update"));
      }
      productDAO.update(product);
      return new ModelAndView("redirect:/product");
   }
}
