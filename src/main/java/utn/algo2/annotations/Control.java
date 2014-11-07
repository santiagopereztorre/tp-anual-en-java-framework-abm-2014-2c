package utn.algo2.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import utn.algo2.core.controles.ControlBase;

@Retention(RetentionPolicy.RUNTIME)
public @interface Control {
	Class<ControlBase> nombre();
}
