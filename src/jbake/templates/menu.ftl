	<!-- Fixed navbar -->
    <div class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="<#if (content.rootpath)??>${content.rootpath}<#else></#if>">&mu;Table</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li><a href="<#if (content.rootpath)??>${content.rootpath}<#else></#if>index.html">News</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Documentation <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="storage.html">Loading and storing tables</a></li>
                <li><a href="filtering.html">Filtering rows</a></li>
                <li><a href="#">Traversing</a></li>
                <li class="divider"></li>
                <li><a href="#">API</a></li>
              </ul>
            </li>
			<li><a href="<#if (content.rootpath)??>${content.rootpath}<#else></#if>about.html">About</a></li>
            
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>
    <div class="container">