package org.simple;

import org.simple.entity.ToDoGroup.ToDoGroup;
import org.simple.entity.ToDoGroup.ToDoGroupRepository;
import org.simple.entity.ToDoItem.ToDoItem;
import org.simple.entity.ToDoItem.ToDoItemRepository;
import org.simple.entity.User.User;
import org.simple.entity.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(Controller.API_PREFIX)
public class Controller {

    public static final String API_PREFIX = "/api";
    public static final String LOGIN = "/login";

    @Autowired
    private ToDoItemRepository toDoItemRepository;

    @Autowired
    private ToDoGroupRepository toDoGroupRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/list-obj")
    public List<ToDoItem> listObj() {
        return toDoItemRepository.findAll();
    }

    @RequestMapping("/list")
    public List<String> list() {
        return toDoItemRepository.getToDoTexts();
    }

    @RequestMapping("/list-group")
    public List<ToDoGroup> listGroup(){
        return toDoGroupRepository.findAllByOrderById();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ToDoItem add(@RequestBody ToDoItem toDoItem) {
        return toDoItemRepository.save(toDoItem);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void delete(@RequestParam Long id) {
        toDoItemRepository.deleteById(id);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PATCH)
    public ToDoItem edit(@RequestBody ToDoItem toDoItem){
        return toDoItemRepository.save(toDoItem);
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping(LOGIN)
    public String getLogin(@RequestBody User user,
                         HttpServletRequest request, HttpServletResponse response){
        if(userRepository.findByLogin(user.getLogin()).size() != 0){
            if(userRepository.findByLogin(user.getLogin()).get(0).getPassword().equals(user.getPassword())){
                request.getSession().setAttribute(AuthorizationInterceptor.USER_PARAM, "user");
            }else{
                return "Password is incorrect";
            }
        }else{
            return "User is not exist.";
        }
        response.setStatus(HttpServletResponse.SC_OK);
        return "Login is success";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signUp(@RequestBody User user){
        if(userRepository.findByLogin(user.getLogin()).size() == 0){
            userRepository.save(user);
            return "Registration is success";
        }else{
            return "User already exist";
        }
    }
}