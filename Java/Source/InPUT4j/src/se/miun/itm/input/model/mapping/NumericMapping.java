package se.miun.itm.input.model.mapping;

import java.lang.reflect.Constructor;
import java.util.List;

import org.jdom2.Element;

import se.miun.itm.input.model.InPUTException;
import se.miun.itm.input.model.Numeric;
import se.miun.itm.input.util.Q;

/**
 * 
 * @author Felix Dobslaw
 *
 * @NotThreadSafe
 */
public class NumericMapping extends Mapping {

	private final Wrapper wrapper;
	
	public NumericMapping(Element mappingElement) throws InPUTException {
		super(mappingElement);
		wrapper = initWrapper(mappingElement);
	}

	/**
	 * For empty Mapping inheritance only.
	 * @param id
	 */
	protected NumericMapping(String id) {
		super(id, null, null);
		wrapper = null;
	}
	
	public NumericMapping(String id, NumericMapping mapping) {
		super(id, mapping.getSetter(), mapping.getGetter());
		wrapper = mapping.getWrapper();
	}
	
	@Override
	public IMapping clone(String id, IMapping mapping) {
		return new NumericMapping(id, this);
	}

	private Wrapper initWrapper(Element mapping) throws InPUTException {
		List<Element> children = mapping.getChildren();
		Element wrap;
		if (children.size() > 0) {
			wrap = children.get(0);
			if (wrap.getName().equals(Q.WRAPPER))
				return new Wrapper(localId, wrap, mapping);
		}

		return null;
	}

	public Class<?> getWrapperClass() {
		return wrapper.getWrapperClass();
	}

	public String getWrapperGetter() {
		return wrapper.getGetter();
	}

	public String getWrapperSetter() {
		return wrapper.getSetter();
	}

	public boolean hasWrapper() {
		return wrapper != null;
	}

	public Wrapper getWrapper() {
		return wrapper;
	}

	public Constructor<?> getWrapperConstructor(Numeric numType)
			throws InPUTException {
		return wrapper.getWrapperConstructor(numType);
	}

	protected String initConstrSignature(Element mapping) {
		if (wrapper != null)
			return wrapper.getConstructorSignature();
		return null;
	}

	@Override
	public IMapping clone(String id, Element mapping) {
		return new NumericMapping(id, this);
	}
}
