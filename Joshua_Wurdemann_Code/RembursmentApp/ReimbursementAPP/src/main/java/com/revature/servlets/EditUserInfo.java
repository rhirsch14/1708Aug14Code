package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.service.Service;
@WebServlet("/editUser")
public class EditUserInfo {


  protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException{

    System.out.println("posting updated user info to db");

    HttpSession session = req.getSession(false);
    if(session != null){
      System.out.println("session is valid");

      Service userService = new Service();

      User user = (User) session.getAttribute("user");

      User someUser = new User();
      someUser.setUserId(user.getUserId());
      someUser.setFirstName(req.getParameter("firstname"));
      someUser.setLastNAme(req.getParameter("lastname"));
      someUser.setEmail(req.getParameter("email"));
      
      
      userService.editUser(someUser);
    //  session.setAttribute("user", user);
     // session.setAttribute("newrem", userService);
// need to change this to view settings
      req.getRequestDispatcher("partials/dashboard.html").forward(req, res);
    }
  }
}