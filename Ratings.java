public class Ratings implements Comparable<Ratings>{

        private int Points;
        private String Comment;

        public Ratings(int point, String Comment) {
            this.Points = point;
            this.Comment = Comment;
        }

        public int getPoints() {
            return Points;
        }

        public void setPoints(int points) {
            this.Points = points;
        }

        public String getComment() {
            return Comment;
        }

        public void setComment(String comment) {
            this.Comment = comment;
        }

        // overriding the compareTo method of Comparable class
		@Override
		public int compareTo(Ratings comparestu) {
			int comparepoints = ((Ratings)comparestu).getPoints();
			return comparepoints - this.Points;
		}
}
