import java.io.*;
import java.util.*;
public class SnakesLadder {
	
	int N, M;
	int snakes[];
	int ladders[];
	int dp[];
	int dp2[];
	public SnakesLadder()throws Exception{
		File file = new File("input.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		N = Integer.parseInt(br.readLine());
        
        M = Integer.parseInt(br.readLine());

	    snakes = new int[N];
		ladders = new int[N];
	    for (int i = 0; i < N; i++){
			snakes[i] = -1;
			ladders[i] = -1;
		}

		for(int i=0;i<M;i++){
            String e = br.readLine();
            StringTokenizer st = new StringTokenizer(e);
            int source = Integer.parseInt(st.nextToken());
            int destination = Integer.parseInt(st.nextToken());

			if(source<destination){
				ladders[source] = destination;
			}
			else{
				snakes[source] = destination;
			}
        }
		int cur =1;
		dp = new int[N+1];
		for(int i=0;i<=N;i++){
			dp[i]=-1;
		}
		dp[0]=0;
		dp[1]=0;
		while(cur<=N){
			int moves = dp[cur];
			for(int i=cur+1;i<=Math.min(N,cur+6);i++){
				if(dp[i]!=-1 && dp[i]<moves+1) {
					moves = dp[i];
					cur = i;
				}
				else if(dp[i]==-1 || dp[i]>moves+1){
					dp[i]=moves+1;
				}
				if(i<N){
					if(ladders[i]!=-1){
						dp[ladders[i]]=dp[i];
					}
					if(snakes[i]!=-1){
						dp[snakes[i]]=Math.min(moves+1,dp[snakes[i]]);
						if(ladders[snakes[i]]!=-1){
							dp[ladders[snakes[i]]]=Math.min(moves+1,dp[ladders[snakes[i]]]);
						}
					}
					
				}
			}
			cur+=6;
		}
		int pos =100;
		dp2 = new int[N+1];
		for(int i=0;i<=N;i++){
			dp2[i]=-1;
		}
		dp2[100]=0;
		while(pos>0){
			int moves = dp2[pos];
			for(int i=pos-1;i>=Math.max(0,pos-6);i--){
				if(dp2[i]==-1 || dp2[i]>moves+1){
					dp2[i]=moves+1;
				}
				if(ladders[i]!=-1){
					dp2[i]=dp2[ladders[i]];
					moves = dp2[i];
					pos =i;
				}
			}
			pos-=6;
		}
		// System.out.println(dp2[16]);
	}
    
	int OptimalMoves()
	{
		/* Complete this function and return the minimum number of moves required to win the game. */
		boolean allcovered = true;
		for(int i=1;i<=6;i++){
			if(snakes[100-i]==-1){
				allcovered =false;
			}
		}
		if(allcovered)return -1;
		
		// System.out.println(dp[100]-dp[86]);
		return dp[N] ;
	}

	int Query(int x, int y)
	{
		/* Complete this function and 
			return +1 if adding a snake/ladder from x to y improves the optimal solution, 
			else return -1. */
			int optimalmoves = dp[N];
			int finalmoves = dp[x]+dp2[y];
			if(finalmoves<optimalmoves){
				System.out.println(finalmoves+" "+optimalmoves);
				return 1;
			}
			return -1;
		
	}

	int[] FindBestNewSnake()
	{
		int result[] = {-1, -1};
		/* Complete this function and 
			return (x, y) i.e the position of snake if adding it increases the optimal solution by largest value,
			if no such snake exists, return (-1, -1) */
		int prev = dp[2];
		int x =2;
		for(int i=8;i<100;i++){
			if(dp[i]<= prev){
				prev = dp[i];
				 x= i;
			}
		}
		int minmoves = dp2[1];
		int y = 2;
		for(int i=1;i<x;i++){
			if(dp2[i]<minmoves){
				minmoves = dp2[i];
				y = i;
			}
		}
		if(x>2 && x>y){
			result[0] = x;
			result[1] = y;
		}
		return result;
	}

    public static void main(String[] args) throws Exception
	{
        SnakesLadder obj = new SnakesLadder();
		int a = obj.OptimalMoves();
		System.out.println(a);
		int b = obj.Query(54,92);
		System.out.println(b);
		int c[] = new int[2];
		c = obj.FindBestNewSnake();
		System.out.println(c[0]+" "+c[1]);
	}
}
