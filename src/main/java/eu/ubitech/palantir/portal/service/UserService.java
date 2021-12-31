package eu.ubitech.palantir.portal.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

@ApplicationScoped
public class UserService {

  @Inject
  EntityManager em;

  private static final Logger logger = Logger.getLogger(UserService.class.getName());

}
