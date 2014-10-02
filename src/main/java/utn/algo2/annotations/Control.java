package utn.algo2.annotations;

import utn.algo2.core.controles.ControlBase;

public @interface Control {
	Class<ControlBase> nombre();
}
