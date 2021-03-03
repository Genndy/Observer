package org.skytech.observer.api.services;

public class PeopleController{

}
/*
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ejb.Singleton;

@Singleton
@Controller
e = new PersonDAO();@RequestMapping("/people")
    //public class PeopleController {
    //    //PersonDAO peopl

    PersonDAO people; // по идее он должен быть final ради безопасности, но пожертвуем этим
    ModelHolderForTestService modelHolder; // for testing. Because i don't know how to transport model to tests

//    @Autowired // мешает
    public PeopleController(){
        people = new PersonDAOWithoutSQLConnect();
    }

    public void setPersonDAO(PersonDAO personDAO){
        this.people = personDAO;
        // I will explain =(
        modelHolder = new ModelHolderForTestService();
    }

    public ModelHolderForTestService getModelHolder() {
        return modelHolder;
    }

    public PeopleController(PersonDAO persondDAO) {
        this.people = persondDAO;
        System.out.println("People controller has been created");
    }

    @GetMapping() // Типа по запросу "/people" прилетает
    public String showPeople(Model model) {
        model.addAttribute("people", people.getAll()); // Ну так мы вот передали модель... В чём проблема то??? АААА
        System.out.println("people showed");
        return "people/all"; // Типа, это представление теперь имеет ту модель...
    }


    @GetMapping("/{id}") // пора бы уже запомнить что фигурные скобки означают прилетающий аргумент
    public String getPersonById(@PathVariable("id") int id, Model model){
        model.addAttribute("person", people.getByIndex(id));
        return "people/person"; // А вообще, можно было бы в полноценный профиль переделать
    }

// import org.springframework.web.bind.annotation.*;

// @GetMapping("/{id}")
// public String getPersonById(@PathVariable("id") int id, Model model){
        model.addAttribute("person", people.getByIndex(id));
        System.out.println("getPersonById");
        if (modelHolder != null){ // It will be architecturally rude... badabum tss
        modelHolder.setModel(model);
        }
        return "people/person";
        }


@GetMapping("/edit/{id}")
public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("person", people.getByIndex(id));
        return "people/edit";
        }


@PatchMapping("/update/{id}")
public String patch(@RequestParam(name = "name", required = false) String name, @PathVariable("id") int id) {
        Person p = new Person(id, name);
        System.err.println(name);
        System.err.println(id);
        people.update(id, p);
        System.err.println("submit executed!");
        return "redirect:/people";
        }
@GetMapping("/new")
//(@ModelAttribute("person") Person person
public String newPerson(@ModelAttribute("person") Person person){
        return "people/new";
        }

@PostMapping("/new")
public String addPerson(@ModelAttribute("person") Person person){
        people.addPerson(person);
        return "redirect:/people";
        }

@DeleteMapping("/delete/{id}") // пока не работает...
public String delete(@PathVariable("id") int id) throws Exception {
        people.delete(id);
        return "redirect:/people";
        }



        // Шо там ещё было? добавление и удаление. Редактирование
        }

 */
