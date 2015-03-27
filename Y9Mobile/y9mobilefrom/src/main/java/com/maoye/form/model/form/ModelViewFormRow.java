package com.maoye.form.model.form;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.maoye.form.model.form.cell.ModelViewFormCell;

public class ModelViewFormRow  extends ModelViewFormElement{
	
	private List<ModelViewFormCell> cells;
	
	
	/**
	 * @param tag
	 */
	public ModelViewFormRow(String tag) {
		super(tag);
		setType(ModelViewFormRow.class.getSimpleName());
	}
	
	
	/**
	 * @param tag
	 */
	public ModelViewFormRow() {
		super();
		setType(ModelViewFormRow.class.getSimpleName());
	}
	
	
	public void addCell(ModelViewFormCell cell){
		if(cells == null){
			cells = new ArrayList<ModelViewFormCell>();
		}
		
		cells.add(cell);
	}
	
	
	public void clearCells(){
		if(cells == null){
			cells.clear();
		}
	}



	public List<ModelViewFormCell> getCells() {
		return cells;
	}


	public void setCells(List<ModelViewFormCell> cells) {
		this.cells = cells;
	}
	
}
