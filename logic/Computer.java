package logic;

public class Computer {
	
	private ReadOnlyBoard board;
	private CellWrapped cell;
	private PlayerColor  color;
	private int counter = 1;
	private int score = 1;
	private int value = 0;
	private int value2 = 0;

	public Computer(ReadOnlyBoard board, PlayerColor color){
		this.color = color;
		this.board = board;
		if(this.color == color.X){
			value = 3;
			value2 = 5;
			counter = 1;
		}else{
			value = 5;
			value2 = 3;
			counter = 2;
		}
	}
	
	public void makeMove(){
		System.out.println(counter);
		if(value == 3){
			makeMoveX();
		}else{
			makeMoveY();
		}
	}
	
	private void makeMoveX(){
		if(counter == 1){
			CellWrapped x = new CellWrapped(0, 0);
			move(x);
		}else if(counter == 3){
			CellWrapped x = new CellWrapped (0, 2);
			if(board.isEmpty(x)){
				move(x);
			}else{
				x = new CellWrapped(2, 0);
				move(x);
			}
		}else if(counter == 5){
			CellWrapped cell = possWin(value);
			if(cell != null){
				move(cell);
			}else{
				cell = possWin(value2);
				if(cell != null){
					move(cell);
				}else{
					move(defence());
				}
			}
		}else if(counter == 7){
			CellWrapped cell = possWin(value);
			if(cell != null){
				move(cell);
			}else{
				cell = possWin(value2);
				if(cell != null){
					move(cell);
				}else{
					move(anyWhere());
				}
			}
		}else if(counter == 9){
			CellWrapped cell = possWin(value);
			if(cell != null){
				move(cell);
			}else{
				cell = possWin(value2);
				if(cell != null){
					move(cell);
				}else{
					move(anyWhere());
				}
			}
		}
	}
	
	private void makeMoveY(){
		if(counter == 2){
			CellWrapped cellWrapped = new CellWrapped(1,1);
			if(board.isEmpty(cellWrapped)){
				move(cellWrapped);
			}else{
				cellWrapped = new CellWrapped(0,0);
				move(cellWrapped);
			}
		}else if(counter == 4){
			CellWrapped cell = possWin(value2);
			if(cell != null){
				move(cell);
			}else{
				move(defence());
			}
		}else if(counter == 6){
			CellWrapped cell = possWin(value);
			if(cell != null){
				move(cell);
			}else{
				cell = possWin(value2);
				if(cell != null){
					move(cell);
				}else{
					move(defence());
				}
			}
		}else if (counter == 8){
			CellWrapped cell = possWin(value);
			if(cell != null){
				move(cell);
			}else{
				cell = possWin(value2);
				if(cell != null){
					move(cell);
				}else{
					move(anyWhere());
				}
			}
		}
	}
	
	private CellWrapped anyWhere(){
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				CellWrapped cellWrapped = new CellWrapped(i, j);
				if(board.isEmpty(cellWrapped)) return cellWrapped;
			}
		}
		return null;
	}
	
	private void move (CellWrapped cell){
		CellValueWrapped cellValue = null;
		if(value == 3){
			cellValue = CellValueWrapped.X;
		}else{
			cellValue = CellValueWrapped.O;
		}
		if(board.isEmpty(cell)){
			board.makeMove(cell, cellValue);
		}
		counter += 2;
	}
	
	private CellWrapped defence (){
		CellWrapped cellWrapped = new CellWrapped(1, 1);
		if(board.isEmpty(cellWrapped)){
			return cellWrapped;
		}else{
			cellWrapped = new CellWrapped(1, 0);
			if(board.isEmpty(cellWrapped)){
				return cellWrapped;
			}else{
				cellWrapped = new CellWrapped(0, 1);
				if(board.isEmpty(cellWrapped)){
					return cellWrapped;
				}else{
					cellWrapped = new CellWrapped(2, 1);
					if(board.isEmpty(cellWrapped)){
						return cellWrapped;
					}else{
						cellWrapped = new CellWrapped(1, 2);
						if(board.isEmpty(cellWrapped)){
							return cellWrapped;
						}
					}
				}
			}
		}
		return null;
	}
	
	
	private CellWrapped possWin(int value){
		CellWrapped cellR;
		CellWrapped cellC;
		for(int i=0; i<3; i++){
			cellC = checkColumn(i);
			cellR = checkRow(i);
			if(cellC != null) return cellC;
			if(cellR != null) return cellR;
		}
		
		cellR = checkRightD(value);
		if(cellR != null) return cellR;
		cellC = checkLeftD(value);
		if(cellC != null) return cellC;
		
		
		return null;
	}
	
	private CellWrapped checkRightD(int value){
		int k = 0;
		int perfect = 0;
		CellValueWrapped cellValue;
		if(value == 3){
			cellValue = CellValueWrapped.X;
			perfect = 18;
		}else{
			cellValue = CellValueWrapped.O;
			perfect = 50;
		}
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				if(i == j){
					CellWrapped cell = new CellWrapped(i, j);
					if(board.isEmpty(cell)){
						k = i;
						score *= 2;
					}else if(board.getValueAt(cell) == cellValue){
						score *= value;
					}
				}
			}
		}
		if(score == perfect){
			CellWrapped cell = new CellWrapped (k, k);
			score = 1;
			return cell;
		}
		score = 1;
		return null;
	}
	
	private CellWrapped checkLeftD(int value){
		int k1 = 0;
		int k2 = 0;
		int perfect = 0;
		CellValueWrapped cellValue;
		if(value == 3){
			cellValue = CellValueWrapped.X;
			perfect = 18;
		}else{
			cellValue = CellValueWrapped.O;
			perfect = 50;
		}
		int i = 0; int j = 2;
		for(int h=0; h<3; h++){
			CellWrapped cell = new CellWrapped(i, j);
			if(board.isEmpty(cell)){
				k1 = i;
				k2 = j;
				score *= 2;
			}else if(board.getValueAt(cell) == cellValue){
				score *= value;
			}
			i++;
			j--;
		}
		if(score == perfect){
			CellWrapped cell = new CellWrapped (k1, k2);
			score = 1;
			return cell;
		}
		score = 1;
		return null;
	}
	
	private CellWrapped checkRow(int n){
		int k = 0;
		int perfect = 0;
		CellValueWrapped cellValue;
		if(value == 3){
			cellValue = CellValueWrapped.X;
			perfect = 18;
		}else{
			cellValue = CellValueWrapped.O;
			perfect = 50;
		}
		for(int i=0; i<3; i++){
			CellWrapped cellWrapped = new CellWrapped(i, n);
			if(board.isEmpty(cellWrapped)){
				k = i;
				score *= 2;
			}else if(board.getValueAt(cellWrapped) == cellValue){
				score *= value;
			}
			if(score == perfect){
				CellWrapped cell = new CellWrapped (k, n);
				score = 1;
				return cell;
			}
		}
		score = 1;
		return null;
	}
	
	private CellWrapped checkColumn (int n){
		int k = 0;
		int perfect = 0;
		CellValueWrapped cellValue;
		if(value == 3){
			cellValue = CellValueWrapped.X;
			perfect = 18;
		}else{
			cellValue = CellValueWrapped.O;
			perfect = 50;
		}
		for(int j=0; j<3; j++){
			CellWrapped cellWrapped = new CellWrapped(n, j);
			if(board.isEmpty(cellWrapped)){
				k = j;
				score *= 2;
			}else if(board.getValueAt(cellWrapped) == cellValue){
				score *= value;
			}
			if(score == perfect){
				CellWrapped cell = new CellWrapped (n, k);
				score = 1;
				return cell;
			}
		}
		score = 1;
		return null;
	}
}
