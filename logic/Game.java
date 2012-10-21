package logic;

import model.*;

public class Game {

//	private final Player xPlayer;
	private final Player oPlayer;
	private final Board board;
	private final ReadOnlyBoard readOnlyBoard;
	private final Referee referee;
	private final Presenter presenter;
	private Computer comp;

	public Game(Board board,  Player oPlayer, Referee referee,
			Presenter presenter) {
		this.board = board;
	//	this.xPlayer = xPlayer;
		this.oPlayer = oPlayer;
		this.referee = referee;
		this.presenter = presenter;
		this.readOnlyBoard = new ReadOnlyBoard(board);
		comp = null;
	}

	public void play() {
		GameStatus gameStatus;
		PlayerColor currentColor = PlayerColor.X;
		
		comp = new Computer (readOnlyBoard, currentColor);
		
		MoveListener xMoveListener = new MoveListener() {
			public void makeMove(CellWrapped cell) {
				board.makeMove(cell.getCell() , CellValue.X);
			}
		};

		MoveListener oMoveListener = new MoveListener() {
			public void makeMove(CellWrapped cell) {
				board.makeMove(cell.getCell(), CellValue.O);
			}
		};

		do {
			gameStatus = referee.getGameStatus(readOnlyBoard);
			presenter.show(readOnlyBoard, gameStatus);
			if (gameStatus != GameStatus.INPROGRESS) {
				break;
			}

			switch (currentColor) {
			case X:
				comp.makeMove();
				break;
			case O:
				oPlayer.makeMove(readOnlyBoard, currentColor, oMoveListener);
				break;
			}

			currentColor = swapColor(currentColor);

		} while (true);
	}

	private PlayerColor swapColor(PlayerColor playerColor) {
		switch (playerColor) {
		case X:
			return PlayerColor.O;
		case O:
			return PlayerColor.X;
		}
		throw new IllegalArgumentException("There is no color for this value.");
	}
}