import javax.inject.Inject

import filters.ScoreFilter
import play.api.http.DefaultHttpFilters
import play.filters.gzip.GzipFilter

class Filters @Inject()(
                         gzip: GzipFilter,
                         score: ScoreFilter
                       ) extends DefaultHttpFilters(gzip, score)
