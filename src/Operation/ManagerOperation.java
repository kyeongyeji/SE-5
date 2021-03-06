package Operation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import Data.Datahandler_manager;
import Information.*;
import Tools.Board;
import Tools.Recruit;
import User.Manager;
import User.Student;

public class ManagerOperation extends Operate{
	//operation manager can do.
	//Write notices, modify&delete notices, ...
	//manager id has this.

	//attributes
	public Manager manager;
	
	//constructor
	public ManagerOperation( Manager m, Board b) {
		super(b);
		this.manager = m;
		// TODO Auto-generated constructor stub
	}
	
	//methods
	public Notice CreateNotice(Scholar sch, String title, String cont, int accept) {
		Notice n = new Notice(sch, accept);
		n.setTitle(title);
		n.setContent(cont);
		return n;
	}//make notice instance with inputs from GUI text boxes.
	
	public ArrayList<Student> recruiting(Notice n) {
		//get recruit information
		Recruit rec = n.getRecruit();
		int selectindex = rec.getRequestedStdList().size();
		ArrayList<Student> selected = new ArrayList<Student>();
		
		//get scholarship information and requested Student list from recruit
		//select students satisfies standard of scholarship.
		//(number of accept)
		
		switch(rec.sch.type) {
		case 1:		//Working scholarship
			//re-sort student array of recruit in order of standard elements(score + income)
			//how about we use variable 'priority'? for example,(a)*income + (1-a)*score (0<=a<=1)
			
			Collections.sort(rec.getRequestedStdList(), new Comparator<Student>() {
				public int compare(Student s1, Student s2) {
					double a = s1.getScore()*0.1 - 0.9*s1.getIncome();
					double b = s2.getScore()*0.1 - 0.9*s1.getIncome();
					
					if(a < b) return -1;
					else if(a == b)
					{
						if(s1.getIncome() < s2.getIncome())
						{
							return -1;
						}
						else
						{
							return 1;
						}
					}
					else return 1;
				}
			});
			
			//student array has been re-sorted with ascending order,
			//so select student from higher index.
			
			for(int i = 0; i < rec.getAccept(); i++) {
				selected.add(rec.getRequestedStdList().get(selectindex--));
			}
			break;
			
		case 2:		//Honor scholarship
			//re-sort student array of recruit in order of standard elements(score)
			Collections.sort(rec.getRequestedStdList(), new Comparator<Student>() {
				public int compare(Student s1, Student s2) {
					if(s1.getScore() < s2.getScore()) return -1;
					else if(s1.getScore() == s2.getScore()) return 0;
					else return 1;
				}
			});
			//student array has been re-sorted with ascending order,
			//so select student from higher index.
			for(int i = 0; i < rec.getAccept(); i++) {
				selected.add(rec.getRequestedStdList().get(selectindex--));
			}
			break;
			
		default :	//error
					
		}
		//let selected students know they are selected with returned list.
		return selected;
	}
	
	public void NoticePosting(Notice n) throws FileNotFoundException, IOException {
		
		super.brd.addNotice(n);
		Datahandler_manager dtm = new Datahandler_manager();
		dtm.board_save(super.brd);
		
		//Board test_board = new Board();
		
	}
	
	public void NoticeDelete(Notice n) throws FileNotFoundException, IOException {
		super.brd.delNotice(n);
		Datahandler_manager dtm = new Datahandler_manager();
		dtm.board_save(super.brd);
		
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
	
	
	
}
