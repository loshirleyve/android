package com.maoye.form.engine;

public class EngineFormCommitResult {

	private int no;
	
	private OberverFormCommitResult commitResult;
	
	/**
	 * @param no
	 * @param commitResult
	 */
	public EngineFormCommitResult(int no, OberverFormCommitResult commitResult) {
		super();
		this.no = no;
		this.commitResult = commitResult;
	}

	public void notifyFormCommitResult(boolean isOk){
		commitResult.notifyFormCommitResult(no, isOk);
	}
	
	
	public interface OberverFormCommitResult {
		public void notifyFormCommitResult(int who, boolean isOK);
	}
	
}
