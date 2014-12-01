package pacman.entries.pacman;

import java.util.ArrayList;
import java.util.Arrays;

import pacman.controllers.Controller;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getAction() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., game.entries.pacman.mypackage).
 */
public class MyPacMan extends Controller<MOVE>
{
	private MOVE myMove=MOVE.NEUTRAL;
	
	public MOVE getMove(Game game,long timeDue)
	{		
		int currentNodeIndex=game.getPacmanCurrentNodeIndex();
		
		//get all active pills
		int[] activePills=game.getActivePillsIndices();
		
		//get all active power pills
		int[] activePowerPills=game.getActivePowerPillsIndices();
		
		
		
		int[] targetNodeIndices;
		 if (activePowerPills.length>0){
			 targetNodeIndices=new int[activePowerPills.length];
			 for(int i=0;i<activePowerPills.length;i++)
					targetNodeIndices[i]=activePowerPills[i];
		 }
		 else{
			 targetNodeIndices=new int[activePills.length];
			 for(int i=0;i<activePills.length;i++)
					targetNodeIndices[i]=activePills[i];
		 }
		 
		//create a target array that includes all ACTIVE pills and power pills
		//int[] targetNodeIndices=new int[activePills.length+activePowerPills.length];
		
		//for(int i=0;i<activePills.length;i++)
		//	targetNodeIndices[i]=activePills[i];
		
		//for(int i=0;i<activePowerPills.length;i++)
		//	targetNodeIndices[activePills.length+i]=activePowerPills[i];		
		
		//return the next direction once the closest target has been identified
		return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),game.getClosestNodeIndexFromNodeIndex(currentNodeIndex,targetNodeIndices,DM.PATH),DM.PATH);	
	}
}