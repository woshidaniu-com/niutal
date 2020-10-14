# Change Log
All notable changes to this project will be documented in this file

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

## 2.17.0
### Changed
 - Removed sourcemaps from build files.

### Added
 - Updated bugfix for FOR-404
 - TableView rendering to all layout components

### Fixed
 - Issue where form components inside containers, inside layout components, inside containers would not render in the
   submission grid.
 - Issue where form components inside data grids, inside layout components, inside containers would not render in the
   submission grid.
 - Issue with the signature component, where the size of the input field was also used for the component which would
   allow for its footer label to be covered by other components.
 - Fixing issue where certain api responses would be stored as json strings rather than error messages
 - Issue where the submit on "action" was passing the wrong object to the submit handlers.
 - Issue where the select search would not reset when they clear out the search box.

## 2.16.6
### Changed
 - Upgrade formio.js to 2.7.3
 - Changed the jsonLogic to use both row and data for logic.
 - No longer use "jsonConditionals" in favor of "conditions.json" property.
 
## Removed
 - jsonLogic from conditionals since that is now handled within the formio.js library.

## 2.16.5
### Removed
 - FOR-404 fix, as it regressed the submission grid for 2.16.4

## 2.16.4
### Fixed
 - Issue where the datetime meridian could become un-synchronized from the component settings format field and show
   different meridian settings
 - Issue where the datetime meridian was not consistent between the submission grid and submission view.
 - Issue where viewing a submission with a datetime component and default value didnt display on the first rendering of
   the edit state.

## 2.16.3
### Fixed
 - Issue where "success" on $http is deprecated and is now using regular promise "then"

## 2.16.2
### Changed
 - Changed how the temp tokens are added to the pdf submission download url.

## 2.16.1
### Changed
 - Changed how the download token is added to the URL to keep ELB from clobbering it.

## 2.16.0
### Added
 - The ability to add inline Resources with an "Add Resource" button on Resource components.
 - Adding iframe support to allow for iframe rendered forms and control over those forms.
 - Configurable input types for checkboxes to be either a checkbox or radio input.

### Changed
 - Upgrade formiojs to 2.6.0
 - Upgrade ng-dialog to 1.1.0

## 2.15.8
### Fixed
 - Fixing issue with meridian support and time selection in the datetime component
 - Changed the yearRange attribute for the datetime component to yearRows and yearColumns, to reflect changes made to
   the angular 1 component.
 - Fixed min/max validation issues with the datetime component
 - Fixed an issue with the survey component and default values rendering.
 - Fixed an issue with the survey component not getting submission values bound in certain view states
 - Fixed merge conflict and missing comma in object properties

## 2.15.7
### Changed
 - The type on the button component to use ng-attr-type so that it works with IE.

## 2.15.6
### Added
 - An index.html example page

### Changed
 - Upgraded ui-select to 0.19.8

## 2.15.4
### Fixed
 - Datetime component won't load existing dates on submissions.

## 2.15.3
### Added
 - Allow translating month part of day component.

### Fixed
 - Could not type in datetime component until a date was selected.

## 2.15.2
### Fixed
 - Upgraded formiojs library to 2.4.2 to fix token persistence issues.

## 2.15.1
### Fixed
 - Fixed placeholder attributes on some fields
 - Fixed angular translate functionality
