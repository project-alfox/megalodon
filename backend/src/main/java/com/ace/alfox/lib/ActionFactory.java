package com.ace.alfox.lib;

import com.ace.alfox.game.interfaces.IAction;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

@Service
public class ActionFactory {
  /** Mappings of /perform/{action} to an IAction. Populated in the AlfoxApplication class. */
  private Map<String, Class<IAction>> actions = new HashMap<>();

  private AutowireCapableBeanFactory beanFactory;

  @PostConstruct
  public void populateActionCache() {
    ClassPathScanningCandidateComponentProvider scanner =
        new ClassPathScanningCandidateComponentProvider(true);
    scanner.addIncludeFilter(new AnnotationTypeFilter(PlayerAction.class));
    for (BeanDefinition bd : scanner.findCandidateComponents("com.ace.alfox.game")) {
      // use the PlayerAction annotation to get the aliased endpoint name
      String alias;
      try {
        Class<?> cl = Class.forName(bd.getBeanClassName());
        PlayerAction playerAction = cl.getAnnotation(PlayerAction.class);
        if (playerAction == null) {
          System.out.println("Received a non-PlayerAction, skipping " + cl.toString());
          continue;
        }
        alias = playerAction.alias();
        actions.put(alias, (Class<IAction>) cl);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
  }

  @Autowired
  public ActionFactory(AutowireCapableBeanFactory beanFactory) {
    this.beanFactory = beanFactory;
  }

  public IAction get(String command) {
    IAction _action = null;
    try {
      _action = beanFactory.createBean(actions.get(command));
    } catch (BeansException e) {
      e.printStackTrace();
    }
    return _action;
  }
}
