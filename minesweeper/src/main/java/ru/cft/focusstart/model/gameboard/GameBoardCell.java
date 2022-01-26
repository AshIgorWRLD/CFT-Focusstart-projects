package ru.cft.focusstart.model.gameboard;

import lombok.Getter;
import lombok.Setter;
import ru.cft.focusstart.model.gameboard.cell.CellMarkStatus;
import ru.cft.focusstart.model.gameboard.cell.CellMineStatus;
import ru.cft.focusstart.model.gameboard.cell.CellRevealStatus;

@Getter
@Setter
public class GameBoardCell {

    private CellMarkStatus markStatus;
    private CellMineStatus mineStatus;
    private int numberStatus;
    private CellRevealStatus revealStatus;

    public GameBoardCell() {
        markStatus = CellMarkStatus.UNMARKED;
        mineStatus = CellMineStatus.NOT_MINED;
        numberStatus = 0;
        revealStatus = CellRevealStatus.UNREVEALED;
    }
}
