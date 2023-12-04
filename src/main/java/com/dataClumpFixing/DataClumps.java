package com.dataClumpFixing;

import java.util.List;
import java.util.Map;

public class DataClumps {

    public static record DataClumpsTypeContext(
            String report_version,
            DataClumpsDetectorContext detector,
            Map<String, DataClumpTypeContext> data_clumps,
            String report_timestamp,
            String target_language,
            ReportSummary report_summary,
            ProjectInfo project_info
    ) {
    }

    public static record DataClumpsDetectorContext() {
        // Define properties for DataClumpsDetectorContext if available in your TypeScript code
    }

    public static record ReportSummary(
            int amount_data_clumps,
            int amount_files_with_data_clumps,
            int amount_classes_or_interfaces_with_data_clumps,
            int amount_methods_with_data_clumps,
            int fields_to_fields_data_clump,
            int parameters_to_fields_data_clump,
            int parameters_to_parameters_data_clump,
            Map<String, Object> additional
    ) {
    }

    public static record ProjectInfo(
            String project_url,
            String project_name,
            String project_version,
            String project_commit_hash,
            String project_tag,
            String project_commit_date,
            int number_of_files,
            int number_of_classes_or_interfaces,
            int number_of_methods,
            int number_of_data_fields,
            int number_of_method_parameters,
            Map<String, Object> additional
    ) {
    }

    public static record DataClumpTypeContext(
            String type,
            String key,
            Double probability,
            String from_file_path,
            String from_class_or_interface_name,
            String from_class_or_interface_key,
            String from_method_name,
            String from_method_key,
            String to_file_path,
            String to_class_or_interface_name,
            String to_class_or_interface_key,
            String to_method_name,
            String to_method_key,
            String data_clump_type,
            Map<String, Object> data_clump_type_additional,
            Map<String, DataClumpsVariableFromContext> data_clump_data
    ) {
    }

    public static record DataClumpsVariableFromContext(
            String key,
            String name,
            String type,
            List<String> modifiers,
            Position position,
            Double probability,
            DataClumpsVariableToContext to_variable
    ) {
    }

    public static record DataClumpsVariableToContext(
            String key,
            String name,
            String type,
            Position position,
            List<String> modifiers
    ) {
    }

    public static record Position(
            int startLine,
            int startColumn,
            int endLine,
            int endColumn
    ) {
    }
 
}
