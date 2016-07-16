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

import com.spring.models.Category;
import com.spring.daos.CategoryDAO;

@Controller
@RequestMapping("/category")
@Transactional
public class CategoryController
{

   @Autowired
   private CategoryDAO categoryDAO;

   @RequestMapping("/form")
   public ModelAndView form(Category category)
   {
      ModelAndView modelAndView = new ModelAndView("category/form-add");
      return modelAndView;

   }

   @RequestMapping(method = RequestMethod.POST)
   public ModelAndView save(@Valid Category category, BindingResult bindingResult)
   {
      if (bindingResult.hasErrors())
      {
         return form(category);
      }
      categoryDAO.save(category);
      return new ModelAndView("redirect:/category");
   }

   @RequestMapping(method = RequestMethod.GET, value = "/{id}")
   public ModelAndView load(@PathVariable("id") Integer id)
   {
      ModelAndView modelAndView = new ModelAndView("category/form-update");
      modelAndView.addObject("category", categoryDAO.findById(id));
      return modelAndView;
   }

   @RequestMapping(method = RequestMethod.GET)
   public ModelAndView list(@RequestParam(defaultValue = "0", required = false) int page)
   {
      ModelAndView modelAndView = new ModelAndView("category/list");
      modelAndView.addObject("paginatedList", categoryDAO.paginated(page, 10));
      return modelAndView;
   }

   //just because get is easier here. Be my guest if you want to change.
   @RequestMapping(method = RequestMethod.GET, value = "/remove/{id}")
   public String remove(@PathVariable("id") Integer id)
   {
      Category category = categoryDAO.findById(id);
      categoryDAO.remove(category);
      return "redirect:/category";
   }

   @RequestMapping(method = RequestMethod.POST, value = "/{id}")
   public ModelAndView update(@PathVariable("id") Integer id, @Valid Category category, BindingResult bindingResult)
   {
      category.setId(id);
      if (bindingResult.hasErrors())
      {
         return new ModelAndView("category/form-update");
      }
      categoryDAO.update(category);
      return new ModelAndView("redirect:/category");
   }
}
