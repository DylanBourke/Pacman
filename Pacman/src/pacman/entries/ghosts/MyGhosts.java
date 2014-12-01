package pacman.entries.ghosts;

import java.util.EnumMap;
import java.util.Random;

import pacman.controllers.Controller;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getActions() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., game.entries.ghosts.mypackage).
 */
public class MyGhosts extends Controller<EnumMap<GHOST,MOVE>>
{
	private final static float CONSISTENCY=0.6f;
	private final static int PAC_PROXIMITY=10;		//if Ms Pac-Man is this close to a power pill, back away
	Random rnd=new Random();
	
	private EnumMap<GHOST, MOVE> myMoves=new EnumMap<GHOST, MOVE>(GHOST.class);
	public enum State {
		    WANDER, CHASE, RETREAT 
	}
	
	State state;
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue)
	{
		myMoves.clear();
		
		//Place your game logic here to play the game as the ghosts
		
		
		for(GHOST ghost : GHOST.values())	//for each ghost
		{
			if(game.getGhostEdibleTime(ghost)>0)
			{
				state = State.RETREAT;
			}
			else if(closeToPacman(game))//if the ghost is close to pacman
			{
				state = State.CHASE;
			}
			else if(!closeToPacman(game))
			{
				state = State.WANDER;
			}
			
			
			if(state == State.RETREAT)//retreat from Ms Pac-Man if edible or if Ms Pac-Man is close to power pill
			{
				myMoves.put(ghost,game.getApproximateNextMoveAwayFromTarget(game.getGhostCurrentNodeIndex(ghost),
						game.getPacmanCurrentNodeIndex(),game.getGhostLastMoveMade(ghost),DM.PATH));
			}
			
			else if(state == State.CHASE) 
			{	//attack Ms Pac-Man otherwise (with certain probability)
				myMoves.put(ghost,game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost),
						game.getPacmanCurrentNodeIndex(),game.getGhostLastMoveMade(ghost),DM.PATH));
			
			}
			
			else if(state == State.WANDER) 
			{	//wander in the general direction of pacman
				
				if(rnd.nextFloat()<CONSISTENCY)			//move towards ms pacman (with certain probability)
					myMoves.put(ghost,game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost),
							game.getPacmanCurrentNodeIndex(),game.getGhostLastMoveMade(ghost),DM.PATH));
				else									//else take a random legal action (to be less predictable)
				{					
					MOVE[] possibleMoves=game.getPossibleMoves(game.getGhostCurrentNodeIndex(ghost),game.getGhostLastMoveMade(ghost));
					myMoves.put(ghost,possibleMoves[rnd.nextInt(possibleMoves.length)]);
				}
			}
			
		}
		
		return myMoves;
	}
	
	private boolean closeToPacman(Game game)//get distance between ghost and ms pacman
    {
    	int pacmanPos = game.getPacmanCurrentNodeIndex();
    	
    	
    		if(game.getShortestPathDistance(pacmanPos,game.getPacmanCurrentNodeIndex())<PAC_PROXIMITY)
    			return true;

        return false;
    }
}