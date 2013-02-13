package gwt.client.game;

import java.util.Arrays;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Window;

import gwt.client.EntryPoint;
import gwt.client.game.vparams.ui.CloneDeposit;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

public class VExpression extends VParams {
	public VExpression() {
	}
	
	

	public static final String vexpression = "expression";
	public static final String settercannotbenegative = "settercannotbenegative";

	static String[] opList = new String[] { "-", "+", "*", "/" };

	@Override
	public void execute(Map<String, Object> map) {

		// ComplexEvaluator engine = new ComplexEvaluator();
		// engine.defineVariable(variableName, value)
		// engine.evaluate(expression).
		// //static util method
		// //if starts with game then use getmap, pass getmap to a method
		// //the method sees that the map is a map and if there is an additional
		// step (pulled off by seeing .
		// //then it pulls it and passes further
		//		
		// //if not game then pull from the associated param map
		//		
		// double d = engine.evaluate(expression);
		PBase pb = new PBase();
		pb.put(VConstants.game, EntryPoint.game);
		pb.getObjMap().putAll(map);
		pexecute(pb);

	}

	protected Object pexecute(PBase map) {
		String expression = getExpression().trim();
		String toSet = vexpression;
		String mainexp = expression;
		if (expression.contains("=")) {

			int index = expression.indexOf('=');
			toSet = expression.substring(0, index).trim();
			mainexp = expression.substring(index + 1).trim();
		} else {
			toSet = null;
		}
		String[] valueList = mainexp.split(" ");
		Object[] computedValues = new Object[valueList.length];
		for (int a = 0; a < valueList.length; a++) {

			computedValues[a] = getValue(valueList[a].trim(), map);
		}
		for (int a = 0; a < computedValues.length; a++) {
			if (computedValues[a] instanceof VExpression) {
				Object exp = ((VExpression) computedValues[a]).pexecute(map);
				computedValues[a] = exp;
			}
		}
		Object valueToSet = computedValues[0];
		if(valueToSet instanceof Integer){
			int first = (Integer) valueToSet;
			for (int a = 1; a < computedValues.length; a += 2) {

				String operator = (String) computedValues[a];
				Integer second = (Integer) computedValues[a + 1];
				if (second == null) {
					return null;
				}
				if ("-".equals(operator)) {
					first = first - second;
				} else if ("+".equals(operator)) {
					first = first + second;
				} else if ("*".equals(operator)) {
					first = first * second;
				} else if ("/".equals(operator)) {
					first = first / second;
				}

				
				
			}
			
			if( first > 0){
				VParams vp=(VParams) AttachUtil.getMappedParam(get(VConstants.vparams));
				vp.execute(map.getObjMap());
			}
			valueToSet = first;
		}
		if(valueToSet == null){
			valueToSet = valueList[0];
		}

		
		
		setValue(toSet, valueToSet, map);

		return valueToSet;
		// multiply add, and do equals
	}

	public static Object getValue(String toGet, PBase map) {
		if (Arrays.asList(opList).contains(toGet)) {
			return toGet;
		}
		int index = toGet.indexOf('.');
		if (index == -1) {

			return map.get(toGet);
		}
		
		
		String nv = toGet.substring(index + 1);
		String nk = toGet.substring(0, index);
		
		if(nk.startsWith("[")){
			nk = (String) getValue(nk.substring(1,nk.length()-1), map);
		}
		
		Object o = map.get(nk);
		// if(o instanceof PBase){
		// o=((PBase)o).getObjMap();
		// }
		if (!(o instanceof PBase)) {
			return null;
		}
		return getValue(nv, (PBase) o);
	}

	public static void setValue(String toSet, Object valueToSet, PBase map) {
		int index = toSet.indexOf('.');
		if (index == -1) {
			map.put(toSet, valueToSet);
			return;
		}
		String nv = toSet.substring(index + 1);
		String nk = toSet.substring(0, index);
		setValue(nv, valueToSet, (PBase) map.get(nk));
	}

	public VExpression(String expression) {
		super();
		put(VConstants.expression, expression);
	}
	public VExpression(String expression, VParams toExecute) {
		super();
		put(VConstants.expression, expression);
		put(VConstants.vparams,toExecute);
	}

	public String getExpression() {
		return getS(VConstants.expression);
	}

	@Override
	public PBase clone() {

		return new VExpression().copyProperties(this);
	}

	public static Object getValue(Map<String, Object> map, String toget) {
		PBase pb = new PBase();
		pb.put(VConstants.game, EntryPoint.game);
		pb.getObjMap().putAll(map);
		return VExpression.getValue(toget, pb);

	}

	public static void setValue(Map<String, Object> map, String toset, int to) {
		PBase pb = new PBase();
		pb.put(VConstants.game, EntryPoint.game);
		pb.getObjMap().putAll(map);
		VExpression.setValue(toset, to, pb);

	}
}