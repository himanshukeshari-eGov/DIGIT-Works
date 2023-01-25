const searchConfig = () => {
    return {
        label : "Search Projects",
        type: 'search',
        sections : {
            search : {
                uiConfig : {
                    headerStyle : null,
                    buttonLabel: 'Search',
                    linkLabel: 'Clear Search',
                    defaultValues : {
                        projectId: "",
                        subProjectId: "",
                        projectName: "",
                        workType: "",
                        createdFromDate: "",
                        createdToDate: ""
                    },
                    fields : [
                        {
                            label:"Project ID",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "projectId"
                            },
                        },
                        {
                            label: "Sub Project ID",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "subProjectId"
                            },
                        },
                        {
                          label: "Name of the Project",
                          type: "text",
                          isMandatory: false,
                          disable: false,
                          populators: { 
                              name: "projectName"
                          }
                        },
                        {
                          label: "Type Of Work",
                          type: "dropdown",
                          isMandatory: false,
                          disable: false,
                          populators: {
                            name: "workType",
                            optionsKey: "name",
                            mdmsConfig: {
                              masterName: "TypeOfWork",
                              moduleName: "works",
                              localePrefix: "WORKS",
                            }
                          }
                        },
                        {
                          label: "Created from Date",
                          type: "date",
                          isMandatory: false,
                          disable: false,
                          populators: { 
                              name: "createdFromDate"
                          }
                        },
                        {
                            label: "Created to Date",
                            type: "date",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "createdToDate"
                            }
                        }
                    ]
                },
                label : "",
                children : {},
                show : true
            },
            searchResult : {
                uiConfig : {},
                label : "",
                children : {},
                show : true
            }
        },
        additionalSections : {}
    }
}

export default searchConfig;