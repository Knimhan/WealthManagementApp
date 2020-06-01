package de.bcgdv.wealthmangement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class StrategyNotFoundException extends RuntimeException {
  private static final long serialVersionUID = -2453166221529927594L;

  public StrategyNotFoundException(String message) {
    super(message);
  }
}
