/*
 * Copyright 2021 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dataClumpFixing;
import java.util.List;
import java.util.Map;

public class DataClumps {

    public static class DataClumpsTypeContext {
        public final String report_version;
        public final DataClumpsDetectorContext detector;
        public final Map<String, DataClumpTypeContext> data_clumps;
        public final String report_timestamp;
        public final String target_language;
        public final ReportSummary report_summary;
        public final ProjectInfo project_info;

        public DataClumpsTypeContext(String report_version, DataClumpsDetectorContext detector,
                                     Map<String, DataClumpTypeContext> data_clumps, String report_timestamp,
                                     String target_language, ReportSummary report_summary, ProjectInfo project_info) {
            this.report_version = report_version;
            this.detector = detector;
            this.data_clumps = data_clumps;
            this.report_timestamp = report_timestamp;
            this.target_language = target_language;
            this.report_summary = report_summary;
            this.project_info = project_info;
        }
    }

    public static class DataClumpsDetectorContext {
        // Define properties for DataClumpsDetectorContext if available in your TypeScript code
    }

    public static class ReportSummary {
        public final Integer amount_data_clumps;
        public final Integer amount_files_with_data_clumps;
        public final Integer amount_classes_or_interfaces_with_data_clumps;
        public final Integer amount_methods_with_data_clumps;
        public final Integer fields_to_fields_data_clump;
        public final Integer parameters_to_fields_data_clump;
        public final Integer parameters_to_parameters_data_clump;
        public final Map<String, Object> additional;

        public ReportSummary(Integer amount_data_clumps, Integer amount_files_with_data_clumps,
                             Integer amount_classes_or_interfaces_with_data_clumps, Integer amount_methods_with_data_clumps,
                             Integer fields_to_fields_data_clump, Integer parameters_to_fields_data_clump,
                             Integer parameters_to_parameters_data_clump, Map<String, Object> additional) {
            this.amount_data_clumps = amount_data_clumps;
            this.amount_files_with_data_clumps = amount_files_with_data_clumps;
            this.amount_classes_or_interfaces_with_data_clumps = amount_classes_or_interfaces_with_data_clumps;
            this.amount_methods_with_data_clumps = amount_methods_with_data_clumps;
            this.fields_to_fields_data_clump = fields_to_fields_data_clump;
            this.parameters_to_fields_data_clump = parameters_to_fields_data_clump;
            this.parameters_to_parameters_data_clump = parameters_to_parameters_data_clump;
            this.additional = additional;
        }
    }

    public static class ProjectInfo {
        public final String project_url;
        public final String project_name;
        public final String project_version;
        public final String project_commit_hash;
        public final String project_tag;
        public final String project_commit_date;
        public final Integer number_of_files;
        public final Integer number_of_classes_or_interfaces;
        public final Integer number_of_methods;
        public final Integer number_of_data_fields;
        public final Integer number_of_method_parameters;
        public final Map<String, Object> additional;

        public ProjectInfo(String project_url, String project_name, String project_version,
                           String project_commit_hash, String project_tag, String project_commit_date,
                           Integer number_of_files, Integer number_of_classes_or_interfaces, Integer number_of_methods,
                           Integer number_of_data_fields, Integer number_of_method_parameters, Map<String, Object> additional) {
            this.project_url = project_url;
            this.project_name = project_name;
            this.project_version = project_version;
            this.project_commit_hash = project_commit_hash;
            this.project_tag = project_tag;
            this.project_commit_date = project_commit_date;
            this.number_of_files = number_of_files;
            this.number_of_classes_or_interfaces = number_of_classes_or_interfaces;
            this.number_of_methods = number_of_methods;
            this.number_of_data_fields = number_of_data_fields;
            this.number_of_method_parameters = number_of_method_parameters;
            this.additional = additional;
        }
    }

    public static class DataClumpTypeContext {
        public final String type;
        public final String key;
        public final Double probability;
        public final String from_file_path;
        public final String from_class_or_interface_name;
        public final String from_class_or_interface_key;
        public final String from_method_name;
        public final String from_method_key;
        public final String to_file_path;
        public final String to_class_or_interface_name;
        public final String to_class_or_interface_key;
        public final String to_method_name;
        public final String to_method_key;
        public final String data_clump_type;
        public final Map<String, Object> data_clump_type_additional;
        public final Map<String, DataClumpsVariableFromContext> data_clump_data;

        public DataClumpTypeContext(String type, String key, Double probability,
                                    String from_file_path, String from_class_or_interface_name,
                                    String from_class_or_interface_key, String from_method_name,
                                    String from_method_key, String to_file_path,
                                    String to_class_or_interface_name, String to_class_or_interface_key,
                                    String to_method_name, String to_method_key,
                                    String data_clump_type, Map<String, Object> data_clump_type_additional,
                                    Map<String, DataClumpsVariableFromContext> data_clump_data) {
            this.type = type;
            this.key = key;
            this.probability = probability;
            this.from_file_path = from_file_path;
            this.from_class_or_interface_name = from_class_or_interface_name;
            this.from_class_or_interface_key = from_class_or_interface_key;
            this.from_method_name = from_method_name;
            this.from_method_key = from_method_key;
            this.to_file_path = to_file_path;
            this.to_class_or_interface_name = to_class_or_interface_name;
            this.to_class_or_interface_key = to_class_or_interface_key;
            this.to_method_name = to_method_name;
            this.to_method_key = to_method_key;
            this.data_clump_type = data_clump_type;
            this.data_clump_type_additional = data_clump_type_additional;
            this.data_clump_data = data_clump_data;
        }
    }

    public static class DataClumpsVariableFromContext {
        public final String key;
        public final String name;
        public final String type;
        public final List<String> modifiers;
        public final Position position;
        public final Double probability;
        public final DataClumpsVariableToContext to_variable;

        public DataClumpsVariableFromContext(String key, String name, String type, List<String> modifiers,
                                            Position position, Double probability, DataClumpsVariableToContext to_variable) {
            this.key = key;
            this.name = name;
            this.type = type;
            this.modifiers = modifiers;
            this.position = position;
            this.probability = probability;
            this.to_variable = to_variable;
        }
    }

    public static class DataClumpsVariableToContext {
        public final String key;
        public final String name;
        public final String type;
        public final Position position;
        public final List<String> modifiers;

        public DataClumpsVariableToContext(String key, String name, String type,
                                           Position position, List<String> modifiers) {
            this.key = key;
            this.name = name;
            this.type = type;
            this.position = position;
            this.modifiers = modifiers;
        }
    }

    public static class Position {
        public final Integer startLine;
        public final Integer startColumn;
        public final Integer endLine;
        public final Integer endColumn;

        public Position(Integer startLine, Integer startColumn, Integer endLine, Integer endColumn) {
            this.startLine = startLine;
            this.startColumn = startColumn;
            this.endLine = endLine;
            this.endColumn = endColumn;
        }
    }
}
