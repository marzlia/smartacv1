if(Ext.ux.grid.GridFilters){
	Ext.apply(Ext.ux.grid.GridFilters.prototype, {
      menuFilterText : '过滤条件'
   });
}

if(Ext.ux.grid.filter.DateFilter) {
	Ext.apply(Ext.ux.grid.filter.DateFilter.prototype, {
		afterText : '后',
		beforeText : '前',
		onText : '在'
	});
}