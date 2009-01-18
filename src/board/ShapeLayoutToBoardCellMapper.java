package board;

import shapes.Shape;
import util.ArrayCellCallback;
import static util.Util.eachCell;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class ShapeLayoutToBoardCellMapper {

    Board board;
    public Shape shape;

    private final RotatorFactory rotators;
    private MovementValidator movementValidator;
    private ShapeRotationValidator rotationValidator;
    Cell[][] shapeCells;
    int zeroIndexRow, zeroIndexColumn;//board cell at (0,0) of the shape matrix

    public ShapeLayoutToBoardCellMapper(Board board) {
        this.board = board;
        rotators = new RotatorFactory();
        movementValidator = new ShapeMovementValidator(this);
        rotationValidator = new ShapeRotationValidator(this);
    }

    public void setNewShape(Shape shape) {
        this.shape = shape;
        shapeCells = boardCellsForNewShape(Board.START_ROW, Board.START_COL);
    }

    private Cell[][] boardCellsForNewShape(final int startRow, final int startCol) {
        final Cell[][] newShapeCells = new Cell[4][4];
        zeroIndexRow = startRow;
        zeroIndexColumn = startCol;

        eachCell(shape.getLayoutArray(), new ArrayCellCallback() {
            @Override
            public void cell(int row, int col) {
                newShapeCells[row][col] =
                        shape.getLayoutArray()[row][col] == 0 ?
                                null :
                                board.getCell(startRow + row, startCol + col).setPopulated(shape);
            }
        });
        return newShapeCells;
    }

    void moveShapeCells(final Movement movement) {
        if (canMove(movement)) {
            setAllCells(shapeCells, false);
            zeroIndexColumn += movement.getColumnChange();
            zeroIndexRow += movement.getRowChange();

            eachCell(shapeCells, new ArrayCellCallback() {
                @Override
                public void cell(int row, int col) {
                    shapeCells[row][col] = cellInNewPosition(movement, shapeCells[row][col]);
                }
            });
        }
    }

    private Cell cellInNewPosition(Movement movement, Cell cell) {
        if (cell != null) {
            return board.getCell(cell.row + movement.getRowChange(),
                    cell.column + movement.getColumnChange()).setPopulated(shape);
        }
        return cell;
    }

    public List<Cell> shapeCellsAsList() {
        List<Cell> cellList = new ArrayList<Cell>();
        for (Cell[] cells : shapeCells) {
            for (Cell cell : cells) {
                if (cell != null) cellList.add(cell);
            }
        }
        return cellList;
    }

    public int startingBoardColumn() {
        return zeroIndexColumn;
    }

    public int startingBoardRow() {
        return zeroIndexRow;
    }

    Cell[][] getBoardCellsForNewShape() {
        return boardCellsForNewShape(startingBoardRow(), startingBoardColumn());
    }

    void setNewShapeCells(Cell[][] newShapeCells) {
        setAllCells(shapeCells, false);
        setAllCells(newShapeCells, true);
        shapeCells = newShapeCells;
    }

    private void setAllCells(final Cell[][] cells, final boolean populated) {
        eachCell(cells, new ArrayCellCallback() {
            @Override
            public void cell(int row, int col) {
                if (cells[row][col] != null) {
                    cells[row][col].setPopulated(populated, shape);
                }
            }
        });
    }

    public Shape getShape() {
        return shape;
    }

    public void rotateShape(Rotation rotation) {
        int[][] newShapeLayoutArray = rotators.rotate(rotation, shape.getLayoutArray());

        if (rotationValidator.isValidRotation(newShapeLayoutArray)) {
            setNewShapeLayout(newShapeLayoutArray);

            setNewShapeCells(getBoardCellsForNewShape());
        }

    }

    private void setNewShapeLayout(final int[][] newShapeMatrix) {

        final int[][] currentMatrix = shape.getLayoutArray();
        eachCell(currentMatrix, new ArrayCellCallback() {
            @Override
            public void cell(int row, int col) {
                currentMatrix[row][col] = newShapeMatrix[row][col];
            }
        });
    }

    boolean canMove(Movement movement) {
        return movementValidator.canMove(movement);
    }


    Cell boardCellAt(int row, int col) {
        return board.getCell(boardRow(row), boardCol(col));
    }


    private int boardCol(int col) {
        return (startingBoardColumn() + col) >= board.getColumns() ?
                board.getColumns() - 1 :
                (startingBoardColumn() + col) < 0 ?
                        0 :
                        startingBoardColumn() + col;
    }

    private int boardRow(int row) {
        return (startingBoardRow() + row) >= board.getRows() ?
                board.getRows() - 1 :
                (startingBoardRow() + row) < 0 ?
                        0 :
                        startingBoardRow() + row;
    }

    public int totalBoardRows() {
        return board.getRows();
    }

    int totalBoardColumns() {
        return board.getColumns();
    }
}
