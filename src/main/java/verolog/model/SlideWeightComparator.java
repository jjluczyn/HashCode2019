package verolog.model;

import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionSorterWeightFactory;

public class SlideWeightComparator  implements SelectionSorterWeightFactory<Solution, Slide> {

    @Override
    public Comparable createSorterWeight(Solution solution, Slide slide) {
        return new SlideDifficultyComparator(slide,slide.getTags().size());
    }

    public static class SlideDifficultyComparator
                implements Comparable<SlideDifficultyComparator> {

            private final Slide s;
            private final int sizeTags;

            public SlideDifficultyComparator(Slide s, int sizeTags) {
                this.s = s;
                this.sizeTags = sizeTags;
            }

            @Override
            public int compareTo(SlideDifficultyComparator other) {
                int res = Integer.compare(this.sizeTags,other.sizeTags);
                //if(res==0) return this.s.getId().compareTo(other.s.getId());
                 if(res==0) return other.s.getId().compareTo(this.s.getId());
                return res;
            }
        }
}
